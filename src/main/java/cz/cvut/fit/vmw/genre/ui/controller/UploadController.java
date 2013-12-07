package cz.cvut.fit.vmw.genre.ui.controller;

import cz.cvut.fit.vmw.genre.GenreAnalyzator;
import cz.cvut.fit.vmw.genre.dial.GenreEnum;
import cz.cvut.fit.vmw.genre.exception.GenreException;
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
        

        if (result.hasErrors()) {
            return new ModelAndView("uploadForm");
        }

        try {
            inputStream = file.getInputStream();

            File newFile = new File("/home/johny/projects/skola/vmw/temp/" + fileName);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            outputStream = new FileOutputStream(newFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            
            outputStream.close();
            newFile = new File("/home/johny/projects/skola/vmw/temp/" + fileName);
            
            GenreAnalyzator analyzator = new GenreAnalyzator();
            Map<GenreEnum, Double> genreMap = analyzator.analyze(newFile);
            
            Map<String, Object> model = new HashMap<>();
            model.put("message", fileName);
            model.put("genres", genreMap); 
            
            return new ModelAndView("showFile", "model", model);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch(GenreException e){
            e.printStackTrace();
        }

              
        return new ModelAndView("uploadForm");
        
    }
}
