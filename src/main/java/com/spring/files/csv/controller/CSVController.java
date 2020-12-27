package com.spring.files.csv.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.files.csv.exception.ModelDataNotFoundException;
import com.spring.files.csv.helper.CSVHelper;
import com.spring.files.csv.message.ResponseMessage;
import com.spring.files.csv.model.ModelData;
import com.spring.files.csv.repository.ModelDataRepository;
import com.spring.files.csv.service.CSVService;

@CrossOrigin("http://localhost:8081")
@RestController
@RequestMapping("/api/csv")
public class CSVController {

  @Autowired
  CSVService fileService;
  
  @Autowired
  ModelDataRepository repository;

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";

    if (CSVHelper.hasCSVFormat(file)) {
      try {
        fileService.save(file);

        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
      }
    }

    message = "Please upload a csv file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
  }
  
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/modeldata")
  ResponseEntity<ModelData> newTutorial(@Valid @RequestBody ModelData newModelData) {
      return new ResponseEntity<>(repository.save(newModelData), HttpStatus.CREATED);
  }
  
  

  @GetMapping("/modeldata")
  public ResponseEntity<List<ModelData>> getAllData() {
    try {
      List<ModelData> modelDataList = fileService.getAllModelData();

      if (modelDataList.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(modelDataList, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @GetMapping("/modeldata/{id}")
  public ResponseEntity<ModelData> findOne(@PathVariable Long id) {
	  
	  Optional<ModelData> optional =  repository.findById(id);  //orElseThrow(() -> new RuntimeException("Model data not found"));
	 
	  if(optional.isEmpty()) {
		 // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	            // throw new RuntimeException("Model data not found in DB");
	   throw new ModelDataNotFoundException(id);
	  } 
	  else return new ResponseEntity<>(optional.get(), HttpStatus.OK);
  }
  
  
  @PutMapping("/modeldata/{id}")
  public ResponseEntity<ModelData> saveOrUpdate(@Valid @RequestBody ModelData newModelData, @PathVariable Long id) {

      return repository.findById(id)
              .map(x -> {
                  x.setName(newModelData.getName());
            	  x.setDescription(newModelData.getDescription());
            	  x.setUpdated_timestamp(newModelData.getUpdated_timestamp());
                 
                  return new ResponseEntity<>(repository.save(x), HttpStatus.OK);
              })
              .orElseGet(() -> {
                  newModelData.setId(id);
                  return new ResponseEntity<>(repository.save(newModelData), HttpStatus.OK);
              });
  }
  
 
  @PatchMapping("/modeldata/{id}")
  public ResponseEntity<ModelData> patch(@Valid @RequestBody Map<String, String> update, @PathVariable Long id) {

      return repository.findById(id)
              .map(x -> {

                  String descr = update.get("description");
                  String name = update.get("name");
                  String timestamp = update.get("updated_timestamp");
                  if (!StringUtils.isEmpty(descr))  x.setDescription(descr);
                  
                  if(! StringUtils.isEmpty(name))  x.setName(name);  
                  if(! StringUtils.isEmpty(timestamp)) x.setUpdated_timestamp(timestamp);
                      
                      return new ResponseEntity<>(repository.save(x), HttpStatus.OK);
                
              })
              .orElseGet(() -> {
                  throw new RuntimeException("Model data with " + id +" not found");
              });

  }

  @DeleteMapping("/modeldata/{id}")
  void deleteBook(@PathVariable Long id) {
      repository.deleteById(id);
  }
  
  
  

  @GetMapping("/download")
  public ResponseEntity<Resource> getFile() {
    String filename = "modeldata.csv";
    InputStreamResource file = new InputStreamResource(fileService.load());

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(file);
  }

}
