package io.daviddm.inventory_audit_api.controller;

import io.daviddm.inventory_audit_api.dto.request.CategoryRequestDTO;
import io.daviddm.inventory_audit_api.dto.response.CategoryResponseDTO;
import io.daviddm.inventory_audit_api.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@Valid @RequestBody CategoryRequestDTO dto,
                                                              @PathVariable @NotNull(message = "El id a actualizar no puede ser nulo")
                                                              @Positive(message = "El id a actualizar debe ser positivo") Long id) {
        return ResponseEntity.ok(categoryService.updateCategory(dto, id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable @NotNull(message = "El id a buscar no puede ser nulo")
                                                               @Positive(message = "El id a buscar debe ser positivo") Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable @NotNull(message = "El id a eliminar no puede ser nulo")
                                                              @Positive(message = "El id a eliminar debe ser positivo") Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
