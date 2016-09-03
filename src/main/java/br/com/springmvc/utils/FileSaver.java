package br.com.springmvc.utils;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Component
public class FileSaver {

	@Autowired
	private AmazonS3Client s3;
	
//Código para escreve o arquivo no repositorio da Amazon
	public String write(String baseFolder, MultipartFile multipartFile) { 
		try { 
			s3.putObject("springmvc", 
						 multipartFile.getOriginalFilename(), 
						 multipartFile.getInputStream(), 
						 new ObjectMetadata());
			return "https://s3.amazonaws.com/springmvc/"+multipartFile.getOriginalFilename()+"?noAuth=true";
		} catch (AmazonClientException | IOException e) { 
			throw new RuntimeException(e); 
		}
	}

	// Código para escrever localmente
//	public String write(String baseFolder, MultipartFile file) {
//		String realPath = "C:/Users/Carlos/Google Drive/Desenvolvimento/Workspace/SpringMVC/SpringMVC/src/main/webapp/"+baseFolder;
//		try {
//			String path = realPath + "/" + file.getOriginalFilename();
//			file.transferTo(new File(path));
//			return baseFolder + "/" + file.getOriginalFilename();
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
}
