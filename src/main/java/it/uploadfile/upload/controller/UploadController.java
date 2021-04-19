package it.uploadfile.upload.controller;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class UploadController {

	@GetMapping("/")
	public String redirect(){
		return "redirect:/upload";
	}

	@GetMapping("/upload")
	public String upload(){
		return "upload";
	}

	@RequestMapping(value = "/onUpload", method = RequestMethod.POST)
	@ResponseBody
	public String singleFileUpload(@RequestParam(value = "file") MultipartFile file) {

		StringBuilder sb = new StringBuilder();

		try {

			InputStream fileInputStream = new BufferedInputStream(file.getInputStream());

			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
			int columnNumber = -1;
			int sheetNumber = -1;
			int rowNumber = -1;

			int numberOfSheets = workbook.getNumberOfSheets();
			for (int n = 0; n < numberOfSheets;n++){
				XSSFSheet sheetAt = workbook.getSheetAt(n);
				int numberOfColumns = sheetAt.getRow(0).getLastCellNum();
				int numberOfRows = sheetAt.getLastRowNum();
				for (int c = 0; c < numberOfColumns; c++){
					for (int r = 0; r < numberOfRows; r++){
						XSSFCell cell = sheetAt.getRow(r).getCell(c);
						String stringCellValue = cell.getStringCellValue();
						if (stringCellValue.equals("Email")) {
							columnNumber = c;
							rowNumber = r+1;
							sheetNumber = n;
							break;
						}
					}
				}
			}
			XSSFSheet sheetFound = workbook.getSheetAt(sheetNumber);
			int row = sheetFound.getLastRowNum();
			for (int i = rowNumber; i <= row; i++){
				sb.append(sheetFound.getRow(i).getCell(columnNumber).getStringCellValue());
				if (i < row) {
					sb.append("; ");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Dati caricati: " + sb.toString();

	}
}
