package com.product.controller;



import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mapping.model.FieldNamingStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.product.Repo.ProductRepo;
import com.product.table.Product;
import com.product.table.ProductDto;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/products")
public class productController {
	@Autowired
private	ProductRepo repo; 
	
	
	
	@GetMapping({"", "/"})
	public String showProductlist(Model model) {
		
		List<Product>products=repo.findAll(); // agar hamko data asanding ya desanding me chiye to findall me (Sort.by(Sort.Direction.DESC,"id"))
		model.addAttribute("products", products);
		return "products/index";
	}
	
	
	@GetMapping("/create")
	public String ShowCreatpage(Model model) {
		
		ProductDto productDto=new ProductDto();
		model.addAttribute("productDto", productDto);
		return "products/createProduct";
		
	}
	
	@PostMapping("/create")
	public String createproduct( @Valid @ModelAttribute ProductDto productDto,BindingResult result) {
		
		if (productDto.getImageFile().isEmpty()) {
			 result.addError(new FieldError("productDto","imageFile","the image file  requried "));
		}
		if (result.hasErrors()) {
			return "products/createProduct";
		}
		
		
		// save image file
		
		MultipartFile image= productDto.getImageFile();	
	          Date date =  new Date();
	       
		String storageFileName= date.getTime()+"_"+image.getOriginalFilename();
		
		try {
			
			String uploadDir="public/";
			Path uploadPath = Paths.get(uploadDir);
			
			if (!Files.exists(uploadPath)) {
				
				Files.createDirectories(uploadPath);
				
				
			}
			
			try (InputStream inputStream=image.getInputStream()) {
				
				Files.copy(inputStream,Paths.get(uploadDir+storageFileName),StandardCopyOption.REPLACE_EXISTING);
				
				
			}
			
		} catch (Exception e) {
			System.out.println("Exception := "+ e.getMessage());
		}
		
		
		Product product= new Product();
		product.setName(productDto.getName());
		product.setBrand(productDto.getBrand());
		product.setCatagroy(productDto.getCatagroy());
		product.setPrice(productDto.getPrice());
		product.setDescripation(productDto.getDescription());
		product.setCreattedt(date);
		product.setImageFileName(storageFileName);
		
		repo.save(product);
		
		return "redirect:/products";
	}
	
	// Upedet prodects
	
	
	@GetMapping("/edit")
	public String ShowEditpage(Model model ,  @RequestParam int id ) {
		
		try {
			
			Product product= repo.findById(id).get();
			model.addAttribute("product", product);
			
			ProductDto pDto= new ProductDto();
			
			pDto.setName(product.getName());
			pDto.setBrand(product.getBrand());
			pDto.setCatagroy(product.getCatagroy());
			pDto.setDescription(product.getDescripation());
			pDto.setPrice(product.getPrice());
			//pDto.setImageFile(product.getImageFileName());
			
			model.addAttribute("productDto", pDto);
			
			
		} catch (Exception e) {
			System.out.println("Execption "+e);
			return"redirect:/products";
		}
		
		return"products/EditProduct";
	}
	
	
	@PostMapping("/edit")
	public String Updetproduct(Model model , @RequestParam int id  , @Valid  @ModelAttribute ProductDto productDto , BindingResult result  ) {
		
		try {
			 Product product= repo.findById(id).get();
			 model.addAttribute("product", product);
			 
			 if (result.hasErrors()) {
				return"products/EditProduct"; 
			}
			 
			 if (!productDto.getImageFile().isEmpty()) {
				 
//				 String uploadDir="public/";
//					Path uploadPath = Paths.get(uploadDir);
				 
				 // delete old image
				 
				String uploddirString="public/";
				
				Path oldimagePath=Paths.get(uploddirString + product.getImageFileName());
				
				try {
					
					Files.delete(oldimagePath);
					
				} catch (Exception e) {
					System.out.println(e);
				}
				
				//Save image 
				
				 MultipartFile image= productDto.getImageFile();
				 Date createDate= new Date();
				 String storgageFileName =createDate.getTime()+"_"+image.getOriginalFilename();
				 
				 try(InputStream inputStream = image.getInputStream()) {
					 
					 Files.copy(inputStream, Paths.get(uploddirString + storgageFileName),StandardCopyOption.REPLACE_EXISTING);
					 
				 }
				 
				 product.setImageFileName(storgageFileName);
				 
			}
			 
			 product.setName(productDto.getName());
			 product.setBrand(productDto.getBrand());
			 product.setCatagroy(productDto.getCatagroy());
			 product.setPrice(productDto.getPrice());
			 product.setDescripation(productDto.getDescription());
			 
			repo.save(product);
		}
		catch (Exception e) {
			
			
		}
		
		
		return "redirect:/products";
	}
	
	//delete
	@GetMapping("/delete")
	public String DeleteProduct(@RequestParam int id) {
		
		try {
			 Product product= repo.findById(id).get();
			 
			 // delete product
			 
			 Path imagePath=Paths.get("public/"+ product.getImageFileName());
			
			 try {
				
				 Files.delete(imagePath);
				 
			} catch (Exception e) {
				System.out.println(e);
			}
			 
			 // delete product
			 
			 repo.delete(product);
		
			 
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/products";
		
	}

}
