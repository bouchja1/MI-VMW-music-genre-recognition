package cz.cvut.fit.vmw.genre.ui.controller;

import cz.cvut.fit.vmw.genre.dial.GenreEnum;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cz.cvut.fit.vmw.genre.ui.model.UploadedFile;
import cz.cvut.fit.vmw.genre.ui.validator.FileValidator;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadController {

    @Autowired
    FileValidator fileValidator;

    @RequestMapping("/fileUploadForm")
    public ModelAndView getUploadForm(
            @ModelAttribute("uploadedFile") UploadedFile uploadedFile,
            BindingResult result) {
        return new ModelAndView("uploadForm");
    }

    @RequestMapping("/fileUpload")
    public ModelAndView fileUploaded(
            @ModelAttribute("uploadedFile") UploadedFile uploadedFile,
            BindingResult result) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        MultipartFile file = uploadedFile.getFile();
        fileValidator.validate(uploadedFile, result);

        String fileName = file.getOriginalFilename();
        Map<GenreEnum, Double> genreMap = new HashMap<>();

        if (result.hasErrors()) {
            return new ModelAndView("uploadForm");
        }

        try {
            inputStream = file.getInputStream();

            File newFile = new File("/home/jan/temp/" + fileName);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            // DUFJA
            // tady si nekde pak posles ten File newFile svemu vstupu a prijde mi vyhodnoceni

            // bouchja1 simulace HashMap            
            genreMap.put(GenreEnum.SKA, new Double(0.5));
            genreMap.put(GenreEnum.ROCK, new Double(0.4));
            genreMap.put(GenreEnum.CHODSKA, new Double(0.1));

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Map<String, Object> model = new HashMap<>();
        model.put("message", fileName);
        model.put("genres", genreMap);           

        return new ModelAndView("showFile", "model", model);
    }
}
