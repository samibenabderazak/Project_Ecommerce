package com.project.controller.user;

import com.project.model.dto.OrderProductDTO;
import com.project.model.dto.OrdersDTO;
import com.project.model.dto.ProductDTO;
import com.project.model.dto.UserDTO;
import com.project.model.mapper.UserMapper;
import com.project.model.response.AddProductToOrderResponse;
import com.project.model.response.ProductPage;
import com.project.service.user.CategoryService;
import com.project.service.user.OrderService;
import com.project.service.user.ProductService;
import com.project.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

@RestController
@CrossOrigin
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;


    //Get the list of categories, total number of products, product list and corresponding page
    @GetMapping(value = {"/product-page"})
    public ResponseEntity<?> showProduct(@RequestParam(value = "page", required = true ) Integer page,
                                         @RequestParam(value = "limit", required = true) Integer limit){
        ProductPage productPage = new ProductPage();
        //Get products in one page
        productPage.setPage(page);
        Pageable pageable = PageRequest.of(page-1, limit);
        productPage.setProductDTOList(productService.findAll(pageable));
        productPage.setTotalPage((int)Math.ceil((double) (productService.totalProduct()) / limit ));
        productPage.setCategoryDTOList(categoryService.findAll());

        return ResponseEntity.ok(productPage);

    }

    //Search products by id
    @GetMapping(value = {"/product-page/{id}"})
    public ResponseEntity<?> findById(@PathVariable Integer id){
        return ResponseEntity.ok(productService.findById(id));
    }

    //Search products by category id
    @GetMapping(value = {"/product-page/category/{id}"})
    public ResponseEntity<?> findProductByCategory(@PathVariable Integer id){
        return ResponseEntity.ok(productService.findByCategory(id));
    }

    //Get product list and corresponding page when searching by price
    @GetMapping(value = {"/product-page/search"})
    public ResponseEntity<?> findProductByPrice(@RequestParam(value = "price") Double price,
                                                @RequestParam(value = "page") Integer page,
                                                @RequestParam(value = "limit") Integer limit){
        ProductPage productPage= new ProductPage();
        productPage.setPage(page);
        Pageable pageable = PageRequest.of(page-1, limit);
        productPage.setProductDTOList(productService.findByPrice(price, pageable));
        int count = (productService.countProductFindByPrice(price));
        productPage.setTotalPage(
                (int)Math.ceil((double) count / limit )
        );
        productPage.setTotalProduct(count);
        return ResponseEntity.ok(productPage);
    }

    //Get the products in the database
    @GetMapping(value = {"/product-page/count"})
    public int totalProduct(){
        return productService.totalProduct();
    }

    //Get related products by product id
    @GetMapping(value = "/single-product/relate/{id}")
    public ResponseEntity<?> findProductRelateById(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(productService.findProductRelateById(id));
    }

    //Add selected product to cart
    @GetMapping(value = {"/product-page/cart"})
    public ResponseEntity<?> addProductToOrder(@RequestParam(value = "iduser") Integer idUser,
                                               @RequestParam( value = "idproduct") Integer idProduct){
        //Search the user's current cart at status = 0

        OrdersDTO ordersDTO = orderService.findOrderByIdUser(idUser);
        ProductDTO productDTO = productService.findById(idProduct);
        if(ordersDTO == null){
            //Create new order for user
            //1. Get user
            UserDTO userDTO = UserMapper.toUserDTO(userService.findUserById(idUser));
            //2.Create a new order
            OrdersDTO newOrderDTO = new OrdersDTO();
            newOrderDTO.setTotalprice(productDTO.getPrice());
            newOrderDTO.setAddress(userDTO.getAddress());
            newOrderDTO.setPhoneNumber(userDTO.getPhoneNumber());
            newOrderDTO.setNameUser(userDTO.getUsername());
            //Convert Timestamp to string
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
            String result = simpleDateFormat.format(userDTO.getCreateDate());
            newOrderDTO.setOrderDate(result);
            newOrderDTO.setStatus(0);
            newOrderDTO.setIdUser(idUser);
            //Add new order to database
            orderService.addOrder(newOrderDTO);
            //Add product to order_product
            OrderProductDTO orderProductDTO = new OrderProductDTO();
            orderProductDTO.setPrice(productDTO.getPrice());
            orderProductDTO.setQuantity(1);
            orderProductDTO.setProductDTO(productDTO);
            orderProductDTO.setOrdersDTO(newOrderDTO);
            //Save orderProduct to database
            orderService.addOrderProduct(orderProductDTO);
            //Return current order and success message
            AddProductToOrderResponse addResponse = new AddProductToOrderResponse(
                                                        orderService.findOrderByIdUser(idUser),
                                                        "Thêm thành công"
                                                    );
            return ResponseEntity.ok(addResponse);
        }else{
            //Add products to an existing order
            //Search in order_product, is there a product you want to buy?
            //If fama, increase by 1
            //If not, create a new order_product
            OrderProductDTO orderProductDTO = orderService
                                                .findOrderProductByIdOrderAndIdProduct(
                                                        ordersDTO.getId(), idProduct
                                                );
            if(orderProductDTO == null){
                //Add new products to order
                System.out.println("The product is not in the order yet");
                OrderProductDTO newOrderProductDTO = new OrderProductDTO();
                ordersDTO.setIdUser(idUser);
                newOrderProductDTO.setOrdersDTO(ordersDTO);
                newOrderProductDTO.setProductDTO(productDTO);
                newOrderProductDTO.setQuantity(1);
                newOrderProductDTO.setPrice(productDTO.getPrice());
                orderService.addOrderProduct(newOrderProductDTO);
                //Update total order price of current order
                double price = ordersDTO.getTotalprice() + newOrderProductDTO.getPrice();
                ordersDTO.setTotalprice(price);
                ordersDTO.setIdUser(idUser);
            }else{
                //Increase the quantity and price of products in the existing order-product
                System.out.println("The product is already in order");
                int quantityProduct = orderProductDTO.getQuantity() + 1;
                double priceOrderProduct = orderProductDTO.getPrice() + productDTO.getPrice();
                orderProductDTO.setQuantity(quantityProduct);
                orderProductDTO.setPrice(priceOrderProduct);
                orderProductDTO.setOrdersDTO(ordersDTO);
                orderProductDTO.setProductDTO(productDTO);
                orderService.updateOrderProduct(orderProductDTO);
                //Update total order cost (order)
                double price = ordersDTO.getTotalprice() + productDTO.getPrice();
                ordersDTO.setTotalprice(price);
                ordersDTO.setIdUser(idUser);
            }
            AddProductToOrderResponse addResponse = new AddProductToOrderResponse(
                    orderService.updateOrder(ordersDTO),
                    "Add to order successfully"
            );
            return ResponseEntity.ok(addResponse);
        }
    }
}
