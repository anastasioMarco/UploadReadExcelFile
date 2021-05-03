package it.uploadfile.upload.controller;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
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

			DataFormatter dataFormatter = new DataFormatter();

			XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

			int numberOfSheets = workbook.getNumberOfSheets();
			for (int n = 0; n < numberOfSheets;n++){    //per ogni sheet
				XSSFSheet sheetAt = workbook.getSheetAt(n);
				int numberOfColumns = sheetAt.getRow(0).getLastCellNum();
				int numberOfRows = sheetAt.getLastRowNum();
				for (int r = 0; r <= numberOfRows; r++){  //per ogni riga
					for (int c = 0; c < numberOfColumns; c++) { //per ogni colonna
						XSSFCell cell = sheetAt.getRow(r).getCell(c);

						sb.append(dataFormatter.formatCellValue(cell));
						sb.append("; ");

					}
					sb.append("<br>");
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Dati caricati:" + "<br>" + sb;

	}
}
