package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CategoryDto;
import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.repository.ProductRepository;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUpload;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ImageUpload imageUpload;
    private ModelMapper modelMapper;


    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductDto> products() {
        return transferData(productRepository.getAllProduct());
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDto productDto) {
        Product product = new Product();
        try {
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                imageUpload.uploadFile(imageProduct);
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            modelMapper.map(productDto, product);
            product.setActivated(true);
            product.setDeleted(false);
            return productRepository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDto productDto) {
        try {
            Product productUpdate = productRepository.getReferenceById(productDto.getId());
            if (imageProduct.getBytes().length > 0) {
                if (imageUpload.checkExist(imageProduct)) {
                    productUpdate.setImage(productUpdate.getImage());
                } else {
                    imageUpload.uploadFile(imageProduct);
                    productUpdate.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                }
            }
            modelMapper.map(productDto, productUpdate);
            return productRepository.save(productUpdate);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void enableById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.setActivated(true);
        product.setDeleted(false);
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.getReferenceById(id);
        product.setDeleted(true);
        product.setActivated(false);
        productRepository.save(product);
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.getReferenceById(id);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<ProductDto> randomProduct() {
        return transferData(productRepository.randomProduct());
    }

    @Override
    public Page<ProductDto> searchProducts(int pageNo, String keyword) {
        List<Product> products = productRepository.findAllByNameOrDescription(keyword);
        List<ProductDto> productDtos = transferData(products);
        Pageable pageable = PageRequest.of(pageNo, 5);
        return toPage(productDtos, pageable);
    }

    @Override
    public Page<ProductDto> getAllProducts(int pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 6);
        List<ProductDto> products = this.products();
        return toPage(products, pageable);
    }

    @Override
    public Page<ProductDto> getAllProductsForCustomer(int pageNo) {
        return null;
    }

    @Override
    public List<ProductDto> findAllCategory(String category) {
        return transferData(productRepository.findAllByCategory(category));
    }

    @Override
    public List<ProductDto> filterHighProducts() {
        return transferData(productRepository.filterHighProducts());
    }

    @Override
    public List<ProductDto> filterLowerProducts() {
        return transferData(productRepository.filterLowerProducts());
    }

    @Override
    public List<ProductDto> listViewProducts() {
        return transferData(productRepository.listViewProduct());
    }

    @Override
    public List<ProductDto> findByCategoryId(Long id) {
        return transferData(productRepository.getProductByCategoryId(id));
    }

    @Override
    public List<ProductDto> searchProducts(String keyword) {
        return transferData(productRepository.searchProducts(keyword));
    }

    private List<ProductDto> transferData(List<Product> products) {
        return products.stream()
                .map(entity -> modelMapper.map(entity, ProductDto.class))
                .toList();
    }

    private Page<ProductDto> toPage(List<ProductDto> list, Pageable pageable) {
        if (pageable.getOffset() > list.size()) {
            return Page.empty();
        }
        int startIndex = (int) pageable.getOffset();
        int endIndex = ((pageable.getOffset() + pageable.getPageSize()) > list.size())
                ? list.size() : (int) (pageable.getOffset() + pageable.getPageSize());
        List<ProductDto> subList = list.subList(startIndex, endIndex);
        return new PageImpl<>(subList, pageable, list.size());
    }

}
