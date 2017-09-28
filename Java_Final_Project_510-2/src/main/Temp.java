package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Temp {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileInputStream fis=new FileInputStream("C:\\Users\\nikit\\Downloads\\1990.gz");
		ZipInputStream zis= new ZipInputStream(fis);
		ZipEntry ze;
		while((ze=zis.getNextEntry())!=null){
			System.out.println(ze.getName());
			zis.closeEntry();
		}
	}

}
