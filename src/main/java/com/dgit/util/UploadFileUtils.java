package com.dgit.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {
	//bean에 등록하지않음
	public static String uploadFile(String uploadPath,String originalName,byte[] fileData) throws Exception{
		File dirPath = new File(uploadPath);
		if(dirPath.exists()==false){
			dirPath.mkdirs();
		}
		
		UUID uid = UUID.randomUUID();//중복방지를 위해서 랜덤값 생성
		String savedName = uid.toString()+"_"+originalName;
		
		//년월일 폴더 만들기
		//한 폴더에 저장할 수 있는 용량이 제한되어 있으므로, 년월일 폴더를 만들도록 한다.
		String savePath = calPath(uploadPath);
		
		//해당 경로에 파일 그릇을 만듬
		//c:/zzz/upload/2018/03/19폴더안에다가 넣어달라
		File target = new File(uploadPath+savePath,savedName);
	
		try {
			//자동으로 data를 target에 넣어준다.
			FileCopyUtils.copy(fileData, target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Thumbnail 처리 (작은이미지를만들어서 보여주는 이미지를 작게하여 서버의 부과를 줄인다)
		String thumbName = makeThumbnail(uploadPath,savePath,savedName);
		
		//원본 이미지 경로와 이름
		//return savePath+"/"+savedName;
		return thumbName;
	}
	
	public static void makeDir(String uploadPath,String... paths){
		for(String path:paths){
			File dirPath = new File(uploadPath+path);
			if(dirPath.exists()==false){
				dirPath.mkdirs();
			}
		}
	}
	
	private static String calPath(String uploadPath){
		Calendar cal = Calendar.getInstance();
		String yearPath =  "/"+cal.get(Calendar.YEAR);
		//String monthPath = yearPath + "/"+cal.get(Calendar.MONTH)+1;
		String monthPath = String.format("%s/%02d", yearPath,cal.get(Calendar.MONTH)+1);
		String datePath = String.format("%s/%02d", monthPath,cal.get(Calendar.DATE));
		
		makeDir(uploadPath,yearPath,monthPath,datePath);
		
		//최종적으로 저장되어야할 폴더
		return datePath;// 2018/03/19폴더 생성 
	}
	
	private static String makeThumbnail(String uploadPath,String path, String filename) throws IOException{
		
		//원본 이미지를 읽어들임
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath+path,filename));
		//원본 이미지를 resize함
		//높이를 맞춤 100px 고정
		BufferedImage deastImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC,Scalr.Mode.FIT_TO_HEIGHT,100);
		
		//thumbnaile이름을 원본이미지와 동일하게 두면 기존 이미지를 덮는다  그래서 바꿔줘야한다.
		String thumbnailName = uploadPath + path+"/s_"+filename;
		
		File newFile = new File(thumbnailName);
		String formatName = filename.substring(filename.lastIndexOf(".")+1);
		
		//resize한 이미지를 thumbnail이미지를 넣는다. 타입도 같이(.jpg,.eng..)모두 대문자로 변경해서
		ImageIO.write(deastImg, formatName.toUpperCase(), newFile);
		
		//upload를 자르고 보냄 //년월일파일명을 보내게 됨
		return thumbnailName.substring(uploadPath.length());
	}
}
