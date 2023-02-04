package uz.limon.chatsecurity.service;

import org.springframework.stereotype.Service;
import uz.limon.chatsecurity.exceptions.ImageNotFoundException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.util.Base64;

@Service
public class FileService {

    public String saveFile(String content, String ext) throws IOException {
        BufferedImage image = null;

        byte[] data = DatatypeConverter.parseBase64Binary(content);
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        image = ImageIO.read(bis);
        bis.close();

        File uploadFile = new File(filePath());

        File outputFile = new File(uploadFile.getPath() + "/" + fileName(ext));
        ImageIO.write(image, ext, outputFile);

        return outputFile.getPath();
    }

    public String filePath(){
        LocalDate now = LocalDate.now();
        Integer year = now.getYear();
        Integer months = now.getMonthValue();
        Integer day = now.getDayOfMonth();


        return String.format("uploads/%d/%d/%d/", year, months, day);
    }

    public synchronized String fileName(String ext){
        return String.format("%d.%s",System.nanoTime(), ext);
    }

    public String getByPath(String content, String ext) throws ImageNotFoundException, IOException {

        File file = new File(content);

        if (!file.exists()) throw new ImageNotFoundException(content);

        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int)file.length()];
        fis.read(data);
        fis.close();
        return Base64.getEncoder().encodeToString(data);
    }
}
