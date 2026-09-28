package com.ecommerce.product.controller;

import com.ecommerce.product.dto.ProductDto;
import com.ecommerce.product.service.CategoryService;
import com.ecommerce.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(
        name = "Product Management",
        description = "APIs for creating, updating, deleting, retrieving, searching, filtering and managing products"
)
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping
    @Operation(
            summary = "Create a product",
            description = "Creates a new product using the provided product details."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product data"
            )
    })
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto) {

        return ResponseEntity.ok(
                productService.createProduct(productDto)
        );
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update a product",
            description = "Updates an existing product using its product ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid product data"
            )
    })
    public ResponseEntity<ProductDto> updateProduct(
            @Parameter(
                    description = "ID of the product to update",
                    example = "1"
            )
            @PathVariable Long id,

            @RequestBody ProductDto productDto) {

        return ResponseEntity.ok(
                productService.updateProduct(id, productDto)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a product",
            description = "Deletes an existing product using its product ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    public ResponseEntity<String> deleteProduct(
            @Parameter(
                    description = "ID of the product to delete",
                    example = "1"
            )
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok("Product deleted Successfully");
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get product by ID",
            description = "Retrieves a product using its unique product ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Product retrieved successfully",
                    content = @Content(schema = @Schema(implementation = ProductDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    public ResponseEntity<ProductDto> getProduct(
            @Parameter(
                    description = "ID of the product",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getProduct(id)
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Retrieves a paginated list of products with sorting support."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Products retrieved successfully"
            )
    })
    public ResponseEntity<Page<ProductDto>> getAllProducts(

            @Parameter(
                    description = "Page number, starting from 0",
                    example = "0"
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Number of products per page",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int size,

            @Parameter(
                    description = "Product field used for sorting",
                    example = "id"
            )
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "asc"
            )
            @RequestParam(defaultValue = "asc") String sortDirection) {

        return ResponseEntity.ok(
                productService.getAllProducts(
                        page,
                        size,
                        sortBy,
                        sortDirection
                )
        );
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search products",
            description = "Searches products by keyword with pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Search results retrieved successfully"
            )
    })
    public ResponseEntity<Page<ProductDto>> searchProducts(

            @Parameter(
                    description = "Keyword to search in product name or description",
                    example = "laptop",
                    required = true
            )
            @RequestParam String keyword,

            @Parameter(
                    description = "Page number, starting from 0",
                    example = "0"
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Number of products per page",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                productService.searchProduct(keyword, page, size)
        );
    }

    @GetMapping("/filter")
    @Operation(
            summary = "Filter products",
            description = "Filters products by category and price range with pagination."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filtered products retrieved successfully"
            )
    })
    public ResponseEntity<Page<ProductDto>> filterProducts(

            @Parameter(
                    description = "Category ID",
                    example = "2"
            )
            @RequestParam(required = false) Long categoryId,

            @Parameter(
                    description = "Minimum product price",
                    example = "500.0"
            )
            @RequestParam(required = false, defaultValue = "0") Double minPrice,

            @Parameter(
                    description = "Maximum product price",
                    example = "50000.0"
            )
            @RequestParam(required = false, defaultValue = "0") Double maxPrice,

            @Parameter(
                    description = "Page number, starting from 0",
                    example = "0"
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Number of products per page",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                productService.filterProduct(
                        categoryId,
                        minPrice,
                        maxPrice,
                        page,
                        size
                )
        );
    }

    @GetMapping("/advancefilter")
    @Operation(
            summary = "Advanced product filtering",
            description = """
                    Filters products using multiple optional criteria including
                    category, minimum price, maximum price and sorting.
                    Results are returned using pagination.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Advanced filtered products retrieved successfully"
            )
    })
    public ResponseEntity<Page<ProductDto>> advanceFilterProducts(

            @Parameter(
                    description = "Category ID",
                    example = "2"
            )
            @RequestParam(required = false) Long categoryId,

            @Parameter(
                    description = "Minimum product price",
                    example = "500.0"
            )
            @RequestParam(required = false, defaultValue = "0") Double minPrice,

            @Parameter(
                    description = "Maximum product price",
                    example = "50000.0"
            )
            @RequestParam(required = false, defaultValue = "0") Double maxPrice,

            @Parameter(
                    description = "Page number, starting from 0",
                    example = "0"
            )
            @RequestParam(required = false, defaultValue = "0") int page,

            @Parameter(
                    description = "Number of products per page",
                    example = "10"
            )
            @RequestParam(required = false, defaultValue = "10") int size,

            @Parameter(
                    description = "Field used for sorting",
                    example = "price"
            )
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "asc"
            )
            @RequestParam(required = false, defaultValue = "asc")
            String sortDirection) {

        return ResponseEntity.ok(
                productService.advancedFilterProduct(
                        categoryId,
                        minPrice,
                        maxPrice,
                        page,
                        size,
                        sortBy,
                        sortDirection
                )
        );
    }

    @PostMapping(
            value = "/{id}/upload-image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(
            summary = "Upload product image",
            description = "Uploads an image file and associates it with the specified product."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Image uploaded successfully",
                    content = @Content(
                            schema = @Schema(implementation = ProductDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid or empty image file"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Product not found"
            )
    })
    public ResponseEntity<ProductDto> uploadImage(

            @Parameter(
                    description = "ID of the product",
                    example = "1"
            )
            @PathVariable Long id,

            @Parameter(
                    description = "Product image file",
                    required = true
            )
            @RequestParam("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok(
                productService.uploadImage(id, file)
        );
    }
}