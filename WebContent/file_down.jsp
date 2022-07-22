<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %>
<%@ page import="com.oreilly.servlet.ServletUtils" %>
<%
	String fileName = request.getParameter("file_name");

	String savePath = "upload";
	/*서버상 실제 경로를 찾아낸다*/
	ServletContext context = getServletContext();
	String sDownloadPath = context.getRealPath(savePath);
	String sFilePath = sDownloadPath + "\\" + fileName;
	
	byte b[] = new byte[4096];
	File oFile = new File(sFilePath);
	
	FileInputStream in = new FileInputStream(sFilePath);
	
	String sMimeType = getServletContext().getMimeType(sFilePath);
	System.out.println("sMimeType>>>"+sMimeType);
	
	if(sMimeType == null) sMimeType = "application/octet-stream";
	
	response.setContentType(sMimeType);
	/*파일명 한글깨짐 방지*/
	String sEncoding = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
	
	//파일다운로드 창 실행
	response.setHeader("Content-Disposition", "attachment; filename= " + sEncoding);
	
	ServletOutputStream out2 = response.getOutputStream();
	
	int numRead;
	//바이트 배열b의 0번 부터 numRead 번 까지 브라우저로 출력
	while((numRead = in.read(b,0,b.length)) != -1){
		out2.write(b,0,numRead);
	}
	//flush()는 현재 버퍼에 저장되어 있는 내용을 클라이언트로 전송하고 버퍼를 비운다. close()와 같이 쓰인다. close내부함수가 flush를 자동으로 호출
	out2.flush();
	out2.close();
	in.close();
%>