package com.dgit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	private String innerUploadPath = "resources/upload";
	
	//id값을 받아와서 주입
	@Resource(name="uploadPath")
	private String outUploadPath;
	
	@RequestMapping(value="/innerUpload", method=RequestMethod.GET)
	public String innerUploadTest(){
		
		return "innerUploadForm";
	}
	
	@RequestMapping(value="/innerUpload",method=RequestMethod.POST)
	public String innerUploadResult(String test,MultipartFile file,HttpServletRequest request,Model model){
		logger.info("test : "+test );
		logger.info("file : "+file.getOriginalFilename());
		
		String root_path = request.getSession().getServletContext().getRealPath("/");
		
		File dirPath = new File(root_path+"/"+innerUploadPath);
		if(dirPath.exists() == false){
			dirPath.mkdirs();
		}
		
		UUID uid = UUID.randomUUID();//중복방지를 위해서 랜덤값 생성
		String savedName = uid.toString()+"_"+file.getOriginalFilename();
		
		//해당 경로에 파일 그릇을 만듬
		File target = new File(root_path+"/"+innerUploadPath,savedName);
	
		try {
			//자동으로 data를 target에 넣어준다.
			FileCopyUtils.copy(file.getBytes(), target);
			model.addAttribute("test", test);
			model.addAttribute("filename",innerUploadPath+"/"+savedName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "innerUploadResult";
	}
	
	//innerMultiUpload
	@RequestMapping(value="/innerMultiUpload",method=RequestMethod.GET)
	public String innerMultiUploadForm(){
		logger.info("innerMultiUpload");
		return "innerMultiUploadForm";
	}
	@RequestMapping(value="/innerMultiUpload",method=RequestMethod.POST)
	public String innerMultiUploadResult(String test,List<MultipartFile> files,HttpServletRequest request,Model model){
		logger.info("innerMultiUpload RESULT POST");
		logger.info(test);
		for(MultipartFile file : files){
			logger.info(file.getOriginalFilename());
		}
		
		String root_path = request.getSession().getServletContext().getRealPath("/");
		
		File dirPath = new File(root_path+"/"+innerUploadPath);
		if(dirPath.exists() == false){
			dirPath.mkdirs();
		}
		List<String> list = new ArrayList<>();
				
		for(MultipartFile file : files){
			UUID uid = UUID.randomUUID();//중복방지를 위해서 랜덤값 생성
			String savedName = uid.toString()+"_"+file.getOriginalFilename();
			File target = new File(root_path+"/"+innerUploadPath,savedName);
			
			try {
				//자동으로 data를 target에 넣어준다.
				FileCopyUtils.copy(file.getBytes(), target);
				list.add(innerUploadPath+"/"+savedName);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("test", test);
		model.addAttribute("files",list);
		return "innerMultiUploadResult";
	}
	
	@RequestMapping(value="/outUpload",method=RequestMethod.GET)
	public String outUploadForm(){
		logger.info("out upload FORM - GET");
		logger.info("outUploadPath :"+ outUploadPath);
		return "outUploadForm";
	}
	
	@RequestMapping(value="/outUpload",method=RequestMethod.POST)
	public String outUploadResult(String test,MultipartFile file,Model model){
		logger.info("out upload RESULT - POST");
		logger.info("test : "+test);
		logger.info("file name : "+file.getOriginalFilename());
		logger.info("file size : "+file.getSize());
		logger.info("file contentType : "+file.getContentType());
		
		File dirPath = new File(outUploadPath);
		if(dirPath.exists()==false){
			dirPath.mkdirs();
		}
		
		UUID uid = UUID.randomUUID();//중복방지를 위해서 랜덤값 생성
		String savedName = uid.toString()+"_"+file.getOriginalFilename();
		
		//해당 경로에 파일 그릇을 만듬
		File target = new File(outUploadPath,savedName);
	
		try {
			//자동으로 data를 target에 넣어준다.
			FileCopyUtils.copy(file.getBytes(), target);
			model.addAttribute("test", test);
			model.addAttribute("filename",savedName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "outUploadResult";
	}
	//파일 드래그 해서 업로드
	@RequestMapping(value="/uploadDrag",method=RequestMethod.GET)
	public String uploadDragForm(){
		logger.info("[uploadDrag] FORM GET");
		return "uploadDragForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/uploadDrag",method=RequestMethod.POST)
	public ResponseEntity<List<String>> uploadDragResult(String test,List<MultipartFile> files,HttpServletRequest request) throws Exception{
		logger.info("[uploadDrag] RESULT POST");
		logger.info("test"+test);
		for(MultipartFile file : files){
			logger.info("filename : " +file.getOriginalFilename());
		}
		ResponseEntity<List<String>> entity = null;
	
		boolean fileSuccess = true;
		
		File dirPath = new File(outUploadPath);
		if(dirPath.exists()==false){
			dirPath.mkdirs();
		}
		
		ArrayList<String> list = new ArrayList<>();
		for(MultipartFile file:files){
			/*UUID uid = UUID.randomUUID();//중복방지를 위해서 랜덤값 생성
			String savedName = uid.toString()+"_"+file.getOriginalFilename();
			
			//해당 경로에 파일 그릇을 만듬
			File target = new File(outUploadPath,savedName);
		*/
			try {
				//자동으로 data를 target에 넣어준다.
				/*FileCopyUtils.copy(file.getBytes(), target);*/
				//함수 만들어서 함수 이용
				String savedName = UploadFileUtils.uploadFile(outUploadPath, file.getOriginalFilename(), file.getBytes());
				list.add(savedName);
			} catch (IOException e) {
				e.printStackTrace();
				fileSuccess=false;
			}

		}
		
		if(fileSuccess) {
			entity = new ResponseEntity<List<String>>(list,HttpStatus.OK);
		} else {
			entity = new ResponseEntity<List<String>>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	//드래그했던 파일을 지운다
	@RequestMapping(value="deleteFile",method= RequestMethod.GET)
	public ResponseEntity<String> deleteFile(String filename){
		ResponseEntity<String> entity = null;
		logger.info("deleteFileName : "+filename);

		try {
			//System.gc();
			
			String front = filename.substring(0,12);
			String end = filename.substring(14);
			
			String orignalFileName = front+end;
		
			
			File file = new File(outUploadPath+filename);
			file.delete();
			
			System.gc();
			
			File orignalFile = new File(outUploadPath+orignalFileName);
		
			orignalFile.delete();
			
			entity = new ResponseEntity<String>("delete success",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>("delete fail",HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/uploadPreview",method=RequestMethod.GET)
	public String uploadPreviewForm(){
		return "uploadPreviewForm";
	}
	@RequestMapping(value="/uploadPreview",method=RequestMethod.POST)
	public String uploadPreviewResult(String writer,MultipartFile file,Model model) throws IOException, Exception{
		logger.info("[uploadPreview] POST");
		logger.info("writer : "+writer);
		logger.info("file : "+file.getOriginalFilename());
		
		File dirPath = new File(outUploadPath);
		if(dirPath.exists()==false){
			dirPath.mkdirs();
		}
		
		try {
			//자동으로 data를 target에 넣어준다.
			String savedName =  UploadFileUtils.uploadFile(outUploadPath, file.getOriginalFilename(), file.getBytes());
			model.addAttribute("writer", writer);
			model.addAttribute("filename",savedName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return "uploadPreviesResult";
	}
	
	@RequestMapping(value="/uploadMultiPreview",method=RequestMethod.GET)
	public String uploadMutiPreviewForm(){
		return "uploadMultiPreviewForm";
	}
	
	@RequestMapping(value="/uploadMultiPreview",method=RequestMethod.POST)
	public String uploadMutiPreviewResult(String writer,List<MultipartFile> files,Model model) throws Exception{
		logger.info("[uploadMultiPreview] POST");
		File dirPath = new File(outUploadPath);
		if(dirPath.exists()==false){
			dirPath.mkdirs();
		}
		
		ArrayList<String> list = new ArrayList<>();
		for(MultipartFile file:files){
			try {
				String savedName = UploadFileUtils.uploadFile(outUploadPath, file.getOriginalFilename(), file.getBytes());
				list.add(savedName);
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
		model.addAttribute("writer",writer);
		model.addAttribute("files",list);
		return "uploadMultiPreviewResult";
	}
	//파일의 로우데이터를 보내준다. out에 저장했기때문에 
	//ajax처리
	@RequestMapping(value="displayFile",method=RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String filename){
		//filename에 년월일이 다 붙은 이름이 돌아온다
		ResponseEntity<byte[]> entity = null;

		logger.info("[filename]:"+filename);
		InputStream in =null;
		
		try {
			//jpg,png인지를 구분
			String formatName = filename.substring(filename.lastIndexOf(".")+1);
			MediaType type = MediaUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(type);
			
			in = new FileInputStream(outUploadPath+filename);
			
			
			entity = new ResponseEntity<>(IOUtils.toByteArray(in),headers,HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
