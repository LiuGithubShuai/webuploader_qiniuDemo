package com.kaishengit.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;

@WebServlet("/img")
public class ImageServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String file = req.getParameter("file");
		
		if(StringUtils.isEmpty(file)){
			resp.sendError(404);
		}else{
			File f = new File("D:/uploadFile",file);
			if(f.exists()){
				OutputStream outputStream = resp.getOutputStream();
				InputStream inputStream = new FileInputStream(f);
				
				IOUtils.copy(inputStream, outputStream);
				outputStream.flush();
				outputStream.close();
				inputStream.close();
			}else{
				resp.sendError(404);
			}
		}
	}

}
