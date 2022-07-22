package com.saeyan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/upload3.do")
public class MultiUploadServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public MultiUploadServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("type/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String savePath = "upload";
		int uploadFileSizeLimit = 5*1024*1024;
		String encType = "UTF-8";
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		try {
			MultipartRequest multi = new MultipartRequest(
					request,
					uploadFilePath,
					uploadFileSizeLimit,
					encType,
					new DefaultFileRenamePolicy());
					
			Enumeration files = multi.getFileNames();
			
			while(files.hasMoreElements()) {
				String file = (String)files.nextElement();
				String file_name = multi.getFilesystemName(file);
				String ori_file_name = multi.getOriginalFileName(file);
				
				out.print("<br> 업로드 파일명: " + file_name);
				out.print("<br> 원본 파일명: " + ori_file_name);
			}
		}catch (Exception e) {
			System.out.println("예외 발생: " + e);

		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("fileList.jsp");
		dispatcher.forward(request,response);
	}

}
