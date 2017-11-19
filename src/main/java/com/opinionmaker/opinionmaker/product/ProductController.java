package com.opinionmaker.opinionmaker.product;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;

@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
    private ProductRepository productRepository;

	@Autowired
	private GridFsTemplate picturesStore; 
	
	@RequestMapping(method = RequestMethod.POST)
    public void createProduct(@RequestBody Product pro){
		productRepository.save(pro);
	}
	
	@RequestMapping(method = RequestMethod.GET)
    public List<Product> findAllProducts(){
		return productRepository.findAll();
	}
	
	@RequestMapping(value="/{productID}/picture",method = RequestMethod.POST)
    public void saveNewPicture(@RequestParam("productID")String productId,@RequestPart("file") MultipartFile file) throws IOException{
		String id = picturesStore.store(file.getInputStream(), file.getName()).getId().toString();
		
		Product product = productRepository.findOne(productId);
		product.getFiles().add(id);
		productRepository.save(product);
	}
	
    @RequestMapping(value="/picture/{pictureID}",method = RequestMethod.GET)
    public ResponseEntity<byte[]> showPicture(@RequestParam("pictureID")String pictureID) throws IOException{
    	GridFSDBFile fs = picturesStore.findOne(new Query(Criteria.where("_id").is(pictureID)));
    	
    	return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(IOUtils.toByteArray(fs.getInputStream()));
	}
}