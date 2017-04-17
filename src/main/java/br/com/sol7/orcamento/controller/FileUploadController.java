package br.com.sol7.orcamento.controller;

import br.com.sol7.orcamento.auth.SessionContext;
import br.com.sol7.orcamento.model.Layout;
import br.com.sol7.orcamento.model.TypeLayout;
import br.com.sol7.orcamento.repository.LayoutRepository;
import br.com.sol7.orcamento.service.LayoutService;
import br.com.sol7.orcamento.util.ApplicationResources;
import br.com.sol7.orcamento.util.MessageUtil;
import br.com.sol7.orcamento.util.ObjectUtil;
import org.apache.commons.io.IOUtils;
import org.lindbergframework.spring.scope.AccessScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javax.annotation.PostConstruct;
import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Normalizer;
import java.util.UUID;

@Controller
@AccessScoped
public class FileUploadController {

    private LayoutRepository layoutRepository;
    private ApplicationResources applicationResources;
    private final SessionContext sessionContext;

    @Autowired
    private LayoutService layoutService;

    private MessageUtil messageUtil;

    private UploadedFile file;

    private byte[] fileByte;

    private Layout layout;

    @Autowired
    public FileUploadController(LayoutRepository layoutRepository, LayoutService layoutService, ApplicationResources applicationResources, SessionContext sessionContext) {
        this.layoutRepository = layoutRepository;
        this.layoutService = layoutService;
        this.applicationResources = applicationResources;
        this.sessionContext = sessionContext;
    }

    @PostConstruct
    public void init(){
        layout = layoutService.getLayout();
        if(ObjectUtil.nullOrEmpty(layout)){
            layout = new Layout();
        }
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getSavePath() {
        return applicationResources.getPath("/layout/");
    }

    public void saveFiles() {
        try {
            layoutService.save(layout);
        } catch (Exception ex){
        }
    }

    public void uploadFileUpload(FileUploadEvent event) throws IOException {
        file = event.getFile();
        fileByte = IOUtils.toByteArray(file.getInputstream());
    }

    public void insertColor(String color){
        layout.setColor(color);
    }

    public void insertBackground(FileUploadEvent event){
        insertFile(event, TypeLayout.BACKGROUND);
    }

    public void insertLogo(FileUploadEvent event){
        insertFile(event, TypeLayout.LOGO);
    }

    public void insertIcon(FileUploadEvent event){
        insertFile(event, TypeLayout.ICON);
    }

    public void insertFile(FileUploadEvent event, TypeLayout type){
        try {
            UUID uuid = UUID.randomUUID();
            String random = uuid.toString().substring(0,6);

            String temp = Normalizer.normalize(event.getFile().getFileName(), java.text.Normalizer.Form.NFD);
            String ext[] = temp.split("\\.");

            String pathFile = getSavePath();
            File upload = new File(pathFile);
            if (!upload.exists()) {
                upload.mkdirs();
            }

            upload = new File(pathFile);
            if (!upload.exists()) {
                upload.delete();
            }

            if (type.equals(TypeLayout.BACKGROUND)){
                createFile(pathFile +"/layout-back-temp"+random+"."+ext[1], event.getFile().getContents());
                layout.setBackground("layout-back-temp"+random+"." + ext[1].toString());
            }

            if (type.equals(TypeLayout.LOGO)){
                createFile(pathFile +"/layout-logo-temp"+random+"."+ext[1], event.getFile().getContents());
                layout.setLogo("layout-logo-temp"+random+"." + ext[1].toString());
            }

            if (type.equals(TypeLayout.ICON)){
                createFile(pathFile +"/layout-icon-temp"+random+"."+ext[1], event.getFile().getContents());
                layout.setIcon("layout-icon-temp"+random+"." + ext[1].toString());
            }

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("rc()");
            messageUtil.sendSimpleMessage("Arquivo inserido com sucesso.", false);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void createFile(String arquivo, byte[] dados) {
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(arquivo));
            imageOutput.write(dados, 0, dados.length);
            imageOutput.close();
        } catch (FileNotFoundException ex) {
      //      Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        /*    Logger.getLogger(UsuarioController.class.getName()).log(
                    Level.SEVERE, null, ex);*/
        }
    }


    public String getPath(){
        return "/default?faces-redirect=true";
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }
}
