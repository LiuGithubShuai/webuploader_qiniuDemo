package com.kaishengit.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;

@WebServlet("/upload")
@MultipartConfig
public class UploaderServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("application/json;charset=UTF-8");
		
		Part part = req.getPart("file");
		InputStream inputStream = part.getInputStream();
		
		String uuid = UUID.randomUUID().toString();
		File file = new File("D:/uploadFile",uuid);
		OutputStream outputStream = new FileOutputStream(file);
		
		IOUtils.copy(inputStream, outputStream);
		outputStream.flush();
		outputStream.close();
		inputStream.close();
		
		Map<String,Object> map = new HashMap<>();
		map.put("success", true);
		map.put("file_path", "http://localhost/img?file="+uuid);
		
		jsonRender(map,resp);
	}

}
