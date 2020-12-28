package mypaint3d;

import javafx.scene.paint.Color;
import java.awt.Point;
import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

/**
 *
 * @author g4l1l3u
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane acpPrincipal;
    @FXML
    private AnchorPane acpCanvas;
    @FXML
    private Canvas canvas;
    @FXML
    private MenuBar menu;
    @FXML
    private Menu menuFile;
    @FXML
    private MenuItem menuFileAbrir;
    @FXML
    private ColorPicker corPicker;
    @FXML
    private Label lbCorObjeto;
    @FXML
    private RadioButton rbAramado;
    @FXML
    private ToggleGroup gr_sl;
    @FXML
    private RadioButton rbSolido;
    @FXML
    private RadioButton rbFacesOcultas;
    @FXML
    private RadioButton rbFrontal;
    @FXML
    private RadioButton rbLateral;
    @FXML
    private RadioButton rbPlanta;
    @FXML
    private ToggleGroup projecao;
    @FXML
    private RadioButton rbPerspectiva;
    @FXML
    private TextField tfDistPerspectiva;
    @FXML
    private Label lbPerspectiva;
    @FXML
    private Button btPerPos;
    @FXML
    private Button brPerNeg;
    @FXML
    private RadioButton rbCabinet;
    @FXML
    private RadioButton rbCavaleira;
    @FXML
    private RadioButton rbAmbiente;
    @FXML
    private RadioButton rbEspecular;
    @FXML
    private RadioButton rbDifusa;
    @FXML
    private RadioButton rbFlat;
    @FXML
    private RadioButton rbPhong;
    @FXML
    private Button btLuz;
    @FXML
    private ToggleGroup sombra;
    @FXML
    private RadioButton rbGouraud;
    @FXML
    private ToggleGroup iluminacao;
    @FXML
    private Slider sldVermelho;
    @FXML
    private Slider sldVerde;
    @FXML
    private Slider sldAzul;
    @FXML
    private Label lbVermelho;
    @FXML
    private Label lbVerde;
    @FXML
    private Label lbAzul;
    @FXML
    private Label lbLocalArquivo;
    @FXML
    private Label lbTamanho;
    @FXML
    private Label lbQtdVert;
    @FXML
    private Label lbArq;
    @FXML
    private Label lbTam;
    @FXML
    private Label lbVert;
    @FXML
    private Label lbFae;
    @FXML
    private Label lbQtdFaces;
    @FXML
    private Label labelEixoX;
    @FXML
    private Label lbEixoX;
    @FXML
    private Label labelEixoY;
    @FXML
    private Label lbEixoY;
    @FXML
    private ImageView imgVArame;
    @FXML
    private ImageView imgVSolido;
    @FXML
    private Label lbEspec;
    @FXML
    private TextField tfEspec;
    @FXML
    private Menu menuAjuda;
    @FXML
    private MenuItem menuAjudaFunc;
    @FXML
    private TextArea taFuncionamento;
    
    private FileChooser fileChooser;
    private File file, fileimg, filelamp;
    
    
    private Objeto obj;
    private double x1, y1, x2, y2, deltax, deltay, deltaz=0;
    private int distancia, especularidade;
    private int[] qtde;
    private boolean pres = false, faceOculta = false, fr = true, lt = false, pl = false, pt = false, cv = false, gb = false, luz = false;
    private Pontos ia, id, ie, ka, kd, ke, Eye, Luz;
    private double axisX, axisY;
    @FXML
    private Menu menuSobre;
    @FXML
    private MenuItem menuSobreNos;
    @FXML
    private TextArea taSobreNos;
    @FXML
    private ImageView imgVLampada;
    @FXML
    private AnchorPane acpPossibilidades;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
        corPicker.setValue(Color.BLUE);
        distancia = 400;
        especularidade = 1;
        tfDistPerspectiva.setText(distancia+"");
        Eye = new Pontos(0, 0, 1);
        Luz = new Pontos(-1, -1, 1);
        ia = new Pontos(0.1, 0.1, 0.1);//Ambiente
        id = new Pontos(0.5, 0.5, 0.5);
        ie = new Pontos(0.5, 0.5, 0.5);
        ka = new Pontos(0.9, 0.9, 0.9); //Ambiente 
        kd = new Pontos(corPicker.getValue().getRed(), corPicker.getValue().getGreen(), corPicker.getValue().getBlue()); //Cor do Objeto - Especular
        ke = new Pontos(0.5, 0.5, 0.5);// Difusa - a cor do brilho
        axisX = 0;
        axisY = 0;
        
        String cam = "/src/imagens/arame.png";
        String inicio = System.getProperty("user.dir");
        cam = inicio.concat(cam);
        file = new File(cam);
        Image img = new Image(file.toURI().toString());
        imgVArame.setImage(img);
        
        cam = "/src/imagens/solido.png";
        cam = inicio.concat(cam);
        fileimg = new File(cam);
        img = new Image(fileimg.toURI().toString());
        imgVSolido.setImage(img);
        
        cam = "/src/imagens/lamp.png";
        cam = inicio.concat(cam);
        filelamp = new File(cam);
        img = new Image(filelamp.toURI().toString());
        imgVLampada.setImage(img);     
        btLuz.setGraphic(imgVLampada);
        
        
        taFuncionamento.setText("Para rotacionar o objeto no eixo Z (Volante) presione a tecla 'SHIFT'\n"
                + "\nPara rotacionar nos eixo xy mantenha pressionado o botao esquerdo do mouse\n"
                + "\nPara transladar o objeto mantenha pressionado o botão direito do mouse\n"
                + "\nPara movimentar a luz mantenha pressionada a tecla 'L', e movimente o mouse\n"
                + "\nQuando voce seleciona um modo de exibição e movimenta ele na tela, quando mudar de modo o objeto"
                + " sera transladado para algum canto da tela, aplique o zoom para localizar e depois translade novamente ao centro\n"
                + "\nPara visualizar de forma correta o efeito da luz, mova o objeto em qualquer direção");
        
        taSobreNos.setText("Este trabalho foi desenvolvido pela turma de Ciência da Computação\n"
                + "Universidade do Oeste Paulista (Unoeste) no ano de 2020\n"
                + "\nMatéria: Computação Gráfica - Professor: Mário Augusto Pazoti\n"
                + "\nDesenvolvedor principal: \bJoão Vitor Sabino R.A: 101527802\n"
                + "\nDesenvolvedores auxiliares: Gabriel Campos Lopes R.A: 101628765 - Vinicius Oliveira Silva R.A: 101628943\n"
                + "\nLinguagem utilizada: JAVA");
    }    

    @FXML
    private void clkFileAbrir(ActionEvent event) {
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Arquivos 3D (*.obj)", "*.obj"));
        file = fileChooser.showOpenDialog(menuFileAbrir.getParentPopup().getScene().getWindow());
        if(file != null)
        {
            obj = new Objeto(file, !faceOculta);
            redesenha(obj, canvas.getGraphicsContext2D());
            
            canvas.setDisable(false);
            deltax = (int)canvas.getWidth()/2;
            deltay = (int)canvas.getHeight()/2;
            
            
            lbLocalArquivo.setText(file.getName());
            lbTamanho.setText(GetSize(file.length()));
            qtde = obj.qtde();
            lbQtdFaces.setText(qtde[0] +"");
            lbQtdVert.setText(qtde[1] +"");
            
            acpPossibilidades.setDisable(false);
        }
    }
    
    private String GetSize(long size)
    {
        String[] unit = { "B", "KB", "MB", "GB", "TB" };
        double d = size;
        int i = 0;
        while(d / 1024 > 1)
        {
            i++;
            d = (double)d / 1024;
        }
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(d) + " " + unit[i];
    }

    
    private Point projecaoFrontal(Pontos p)
    {
        return new Point((int)p.getX(), (int)p.getY());
    }
    
    private Point projecaoPlanta(Pontos p)
    {
        return new Point((int)p.getX(), (int)p.getZ());
    }
    
    private Point projecaoLateral(Pontos p)
    {
        return new Point((int)p.getZ(), (int)p.getY());
    }
    
    private Point projecaoPerspectiva(Pontos P)
    {
        double x = P.getX(), y = P.getY(), z = P.getZ();
        double nx, ny;
        if(tfDistPerspectiva.getText().trim() != null && Integer.parseInt(tfDistPerspectiva.getText()) > 0)
            distancia = Integer.parseInt(tfDistPerspectiva.getText());
        nx = (x*distancia)/(z+distancia);
        ny = (y*distancia)/(z+distancia);
        return new Point((int)nx, (int)ny);
    }
    
    private Point projecaoObliquas(Pontos P, double l)
    {
        double x = P.getX(), y = P.getY(), z = P.getZ();
        double nx = x + z * l * Math.cos(45 * Math.PI / 180.0);
        double ny = y + z * l * Math.sin(45 * Math.PI / 180.0);
        return new Point((int)nx, (int)ny);
    }
    
    private void altera()
    {
        fr = pl = lt = pt = cv = gb = false;
    }
    
    private Point projecao(Pontos p)
    {
        if (rbFrontal.isSelected()) {
            altera();
            fr = true;
            return projecaoFrontal(p);
        } else if (rbLateral.isSelected()) {
            altera();
            lt = true;
            return projecaoLateral(p);
        } else if (rbPlanta.isSelected()) {
            altera();
            pl = true;
            return projecaoPlanta(p);
        } else if (rbPerspectiva.isSelected()) {
            altera();
            pt = true;
            return projecaoPerspectiva(p);
        } else if (rbCavaleira.isSelected()) {
            altera();
            cv = true;
            return projecaoObliquas(p, 1);
        } else if (rbCabinet.isSelected()) {
            altera();
            gb = true;
            return projecaoObliquas(p, 0.5);
        }

        return null;
    }
    
    private boolean qual(int i)
    {
        if(fr || pt || cv || gb)
            return obj.getVNFace(i).getZ() >= 0.0;
        if(pl)
            return obj.getVNFace(i).getY() <= 0.0;
        if(lt)
            return obj.getVNFace(i).getX() <= 0.0;
        return false;
    }
    
    
    private void redesenha(Objeto aux, GraphicsContext gc)
    {
        gc.getCanvas().getGraphicsContext2D().clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());//Limpa o canvas
        
        if(rbAramado.isSelected())
        {
            for(int i = 0; i < aux.getListaFaces().size(); i++)
            {
                if(!faceOculta)
                {
                    if(qual(i))
                    {
                        Reta r = new Reta();
                        int a;
                        for (a = 0; a < aux.getListaFaces().get(i).size() - 1; a++) 
                        {
                            r.bresenham(projecao(aux.getListFacesPos(i, a)), projecao(aux.getListFacesPos(i, a + 1)));
                            desenhaRetaPoli(r, gc);
                        }
                        r.bresenham(projecao(aux.getListFacesPos(i, a)), projecao(aux.getListFacesPos(i, 0)));
                        desenhaRetaPoli(r, gc);
                    }
                }
                else
                {
                    Reta r = new Reta();
                    int a;
                    for (a = 0; a < aux.getListaFaces().get(i).size() - 1; a++) 
                    {
                        r.bresenham(projecao(aux.getListFacesPos(i, a)), projecao(aux.getListFacesPos(i, a + 1)));
                        desenhaRetaPoli(r, gc);

                    }
                    r.bresenham(projecao(aux.getListFacesPos(i, a)), projecao(aux.getListFacesPos(i, 0)));
                    desenhaRetaPoli(r, gc);
                }
            }
        }
        else
        {
            if(rbSolido.isSelected())
            {
                if(!tfEspec.getText().trim().isEmpty())
                    especularidade = Integer.parseInt(tfEspec.getText());
                else
                    especularidade = 1;
                if(rbPhong.isSelected())
                {
                    Phong(canvas.getGraphicsContext2D(), obj, (int)(deltax), (int)(deltay), especularidade, ia, id, ie, ka, kd, ke);
                }
                if(rbFlat.isSelected())
                {
                    Flat(canvas.getGraphicsContext2D(), obj, (int)(deltax), (int)(deltay), especularidade, ia, id, ie, ka, kd, ke);
                }
                if(rbGouraud.isSelected())
                {
                    Gouraud(canvas.getGraphicsContext2D(), obj, (int)(deltax), (int)(deltay), especularidade, ia, id, ie, ka, kd, ke);
                }
            }
        }   
    }
       
    private void desenhaRetaPoli(Reta r, GraphicsContext gc)
    {
       int x = ((int)gc.getCanvas().getWidth()/2), y =((int)gc.getCanvas().getHeight()/2);
       List<Point> coord = r.getCoordenadas();
       for(int i = 0; i < coord.size(); i++)
               writePixel((int)coord.get(i).getX()+x, (int)coord.get(i).getY()+y, gc.getCanvas().getGraphicsContext2D(), corPicker.getValue());
    }
    
    private void writePixel(int x, int y, GraphicsContext gc, Color cor)
    {
        gc.fillRect(x, y, 1, 1);
        gc.setFill(cor);
    }

    
    @FXML
    private void scroll(ScrollEvent event) {
        
        double zoom = 0;
        if(event.getDeltaY() < 0)
        {
            zoom = 0.8;
        }
        else
        {
            zoom = 1.1;
        }
        obj.escala(zoom, zoom, zoom);
        redesenha(obj, canvas.getGraphicsContext2D());
    }

    @FXML
    private void movimenta(MouseEvent event) {
        x2 = event.getX();
        y2 = event.getY();
        deltax = x2-x1;
        deltay = y2-y1;
        //System.out.println(deltax + " " + deltay);
        axisX += deltax;
        axisY += deltay;
        lbEixoX.setText(axisX + "º");
        lbEixoY.setText(axisY + "º");
        
        if(!luz)
        {
            if(event.isSecondaryButtonDown())
            {
                obj.translacao(deltax, deltay, deltaz);
            }
            else
            {
                if(event.isPrimaryButtonDown())
                {
                    if(pres)
                    {
                        double ang; 
                        if(Math.abs(deltay) > Math.abs(deltax)) 
                            ang =  -deltay;
                        else
                            ang = deltax;
                        obj.rotacaoZ(ang, !faceOculta);
                    }
                    else
                    {
                        obj.rotacaoX(-deltay, !faceOculta);
                        obj.rotacaoY(deltax, !faceOculta);
                    }

                }
            }
        }
        redesenha(obj, canvas.getGraphicsContext2D());
        x1 = x2;
        y1 = y2;
    }

    @FXML
    private void mouseDown(MouseEvent event) {
        x1 = event.getX();
        y1 = event.getY();
        shit();
    }
    
    private void shit()
    {
        Scene scene = canvas.getScene();
        scene.setOnKeyPressed(e -> {
        if (e.getCode() == KeyCode.SHIFT) 
        {
            pres = true;
        }
        else
        {
            if(e.getCode() == KeyCode.L)
            {
                atulizaLuz(x1 - ((int)btLuz.getWidth() >> 1), y1 - btLuz.getHeight());
                redesenha(obj, canvas.getGraphicsContext2D());
                luz = true;
            }
        }
        });
    }

    @FXML
    private void mouseUp(MouseEvent event) {
        pres = false;
        luz = false;
    }

    @FXML
    private void clkFaceOculta(ActionEvent event) {
        if(rbFacesOcultas.isSelected())
           faceOculta = true;
        else
            faceOculta = false;
    }

    @FXML
    private void clkRbLateral(ActionEvent event) {
        canvas.setDisable(true);
        redesenha(obj, canvas.getGraphicsContext2D());
    }

    @FXML
    private void clkRbFrontal(ActionEvent event) {
        redesenha(obj, canvas.getGraphicsContext2D());
        canvas.setDisable(false);
    }

    @FXML
    private void clkRbPlanta(ActionEvent event) {
        canvas.setDisable(true);
        redesenha(obj, canvas.getGraphicsContext2D());
    }

    @FXML
    private void clkBtPerPos(ActionEvent event) {
        distancia+=50;
        tfDistPerspectiva.setText(distancia+"");
        redesenha(obj, canvas.getGraphicsContext2D());
    }

    @FXML
    private void clkBtPerNeg(ActionEvent event) {
        distancia-=50;
        tfDistPerspectiva.setText(distancia+"");
        redesenha(obj, canvas.getGraphicsContext2D());
    }

    @FXML
    private void clkRbPerspectiva(ActionEvent event) {
        redesenha(obj, canvas.getGraphicsContext2D());
    }

    @FXML
    private void clkRbCabinet(ActionEvent event) {
        rbFacesOcultas.setSelected(true);
        faceOculta = true;
        redesenha(obj, canvas.getGraphicsContext2D());
    }

    @FXML
    private void clkRbCavaleira(ActionEvent event) {
        rbFacesOcultas.setSelected(true);
        faceOculta = true;
        redesenha(obj, canvas.getGraphicsContext2D());
    }
    
    private void atulizaLuz(double x, double y)
    {
        btLuz.relocate(x, y);
        
        x = x + ((int)btLuz.getWidth() >> 1) - canvas.getLayoutX();
        y = y + ((int)btLuz.getHeight()>> 1) - canvas.getLayoutY();
        
        Luz = new Pontos(x - (obj.centroPoligno().getX() + deltax), y - (obj.centroPoligno().getY()) + deltay, 1);
        Luz = Luz.normalizar();
        Luz.setZ(1);
    }


    @FXML
    private void btLuzSolta(MouseEvent event) {
        
        System.out.println(event.getX() + " " + event.getY());
    }
    
    
    private double[][] gerarZBuffer(int width, int height)
    {
        double[][] zbuffer = new double[width][height];
        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
                zbuffer[x][y] = -999999999;
        }
        return zbuffer;
    }
    
    private boolean inImage(GraphicsContext gc, int x, int y)
    {
        return x >= 0 && x < gc.getCanvas().getWidth() && y >= 0 && y < gc.getCanvas().getHeight();
    }
    
    private void Flat(GraphicsContext gc, Objeto obj, int tx, int ty, int n, Pontos ia, Pontos id, Pontos ie, Pontos ka, Pontos kd, Pontos ke)
    {
        int height = (int)gc.getCanvas().getHeight(), width = (int)gc.getCanvas().getWidth();
        double[][] zbuffer = gerarZBuffer(width, height);
        ET et;
        obj.backFaceCulling();
        for(int i = 0; i < obj.getListaFaces().size(); i++)
        {
            if(obj.getVNFace(i).getZ() >= 0.0)
            {
                et = obj.gerarETFaceFlat(i, height, tx, ty, Luz, Eye, n, ia, id, ie, ka, kd, ke);
                scanLineFaceFlat(gc, et, zbuffer);
            }
        }
    }
    
    private void scanLineFaceFlat(GraphicsContext gc, ET et, double[][] zbuffer)
    {
        List<NoAET> lista;
        double z, inczx;
        int y = 0;
        while(y < et.size() && et.get(y) == null)
            y++;
        AET aet = new AET(), aetAux;
        do
        {
            if(et.get(y) != null)
            {
                aet.add(et.get(y).getLista());
            }
            aetAux = new AET();
            
            for (NoAET no : aet.getLista()) {
                if(no.getYmax() > y)
                    aetAux.add(no);
            }
            aet = aetAux;
            aet.ordena();
            lista = aet.getLista();
            for(int i = 0, x, x2; i < lista.size() - 1; i+=2)
            {
                x = (int)Math.round(lista.get(i).getXmin());
                x2 = (int)Math.round(lista.get(i+1).getXmin());
                z = lista.get(i).getZmin();
                inczx = (lista.get(i+1).getZmin() - lista.get(i).getZmin())/(x2 - x);
                for(int c = x, c2 = (int)(lista.get(i+1).getXmin()); c <= c2; c++)
                {
                    if(inImage(gc, x, y) && z > zbuffer[x][y])
                    {
                        zbuffer[x][y] = z;
                        Color cor = Color.rgb((int)lista.get(i).getRxmin(), (int)lista.get(i).getGymin(), (int)lista.get(i).getBzmin());
                        writePixel(x, y, gc, cor);
                    }
                    z += inczx;
                    x++;
                }
            }
            for(int i = 0; i < aet.getLista().size(); i++)
            {
                aet.getLista().get(i).setXmin(aet.getLista().get(i).getXmin() + aet.getLista().get(i).getIncx());
                aet.getLista().get(i).setZmin(aet.getLista().get(i).getZmin() + aet.getLista().get(i).getInczy());
            }
            y++;
        }while(aet.getLista().size() > 0);
    }
    
    private void Gouraud(GraphicsContext gc, Objeto obj, int tx, int ty, int n, Pontos ia, Pontos id, Pontos ie, Pontos ka, Pontos kd, Pontos ke)
    {
        int height = (int)gc.getCanvas().getHeight(), width = (int)gc.getCanvas().getWidth();
        double[][] zbuffer = gerarZBuffer(width, height);
        ET et;
        obj.backFaceCulling();
        obj.atualizaVetNormaisVertices();
        for(int i = 0; i < obj.getListaFaces().size(); i++)
        {
            if(obj.getVNFace(i).getZ() >= 0.0)
            {
                et = obj.gerarETFaceGourand(i, height, tx, ty, Luz, Eye, n, ia, id, ie, ka, kd, ke);
                scanLineFaceGouraud(gc, et, zbuffer);
            }
        }
    }
    
    private void scanLineFaceGouraud(GraphicsContext gc, ET et, double[][] zbuffer)
    {
        List<NoAET> lista;
        double z, inczx;
        int y = 0;
        AET aet = new AET(), aetAux;
        while (y < et.size() && et.get(y) == null) {
            y++;
        }
        do 
        {
            if (et.get(y) != null) {
                aet.add(et.get(y).getLista());
            }
            aetAux = new AET();
            for (NoAET no : aet.getLista()) {
                if (no.getYmax() > y) {
                    aetAux.add(no);
                }
            }
            aet = aetAux;
            aet.ordena();
            lista = aet.getLista();
            for (int i = 0, x, x2; i < lista.size() - 1; i += 2) 
            {
                x = (int)Math.round(lista.get(i).getXmin());
                x2 = (int)Math.round(lista.get(i + 1).getXmin());
                z = lista.get(i).getZmin();
                double r, g, b, incrx, incgx, incbx, dx = x2 - x;
                r = lista.get(i).getRxmin();
                g = lista.get(i).getGymin();
                b = lista.get(i).getBzmin();
                incrx = (lista.get(i + 1).getRxmin() - lista.get(i).getRxmin()) / dx;
                incgx = (lista.get(i + 1).getGymin() - lista.get(i).getGymin()) / dx;
                incbx = (lista.get(i + 1).getBzmin() - lista.get(i).getBzmin()) / dx;
                inczx = (lista.get(i + 1).getZmin() - lista.get(i).getZmin()) / dx;
                
                while (x <= x2) 
                {
                    if (inImage(gc, x, y) && z > zbuffer[x][y]) 
                    {
                        zbuffer[x][y] = z;
                        Color c = Color.rgb((int)r, (int)g, (int)b);
                        writePixel(x, y, gc, c);
                    }
                    r += incrx;
                    g += incgx;
                    b += incbx;
                    z += inczx;
                    x++;
                }
            }
            for (NoAET noAET : aet.getLista()) {
                noAET.setXmin(noAET.getXmin() + noAET.getIncx());
                noAET.setZmin(noAET.getZmin() + noAET.getInczy());
                noAET.setRxmin(noAET.getRxmin()+ noAET.getIncrx());
                noAET.setGymin(noAET.getGymin()+ noAET.getIncgy());
                noAET.setBzmin(noAET.getBzmin() + noAET.getIncbz());
            }
            y++;
        } while (aet.getLista().size() > 0);
    }
    
    
    private void Phong(GraphicsContext gc, Objeto obj, int tx, int ty, int n, Pontos ia, Pontos id, Pontos ie, Pontos ka, Pontos kd, Pontos ke)
    {
        int height = (int)gc.getCanvas().getHeight(), width = (int)gc.getCanvas().getWidth();
        double[][] zbuffer = gerarZBuffer(width, height);
        ET et;
        obj.backFaceCulling();
        obj.atualizaVetNormaisVertices();
        for(int i = 0; i < obj.getListaFaces().size(); i++)
        {
            if(obj.getVNFace(i).getZ() >= 0.0)
            {
                et = obj.gerarETFacePhong(i, height, tx, ty);
                scanLineFacePhong(gc, et, zbuffer, n, ia, id, ie, ka, kd, ke);
            }
        }
    }
    
    private void scanLineFacePhong(GraphicsContext gc, ET et, double[][] zbuffer, int n, Pontos ia, Pontos id, Pontos ie, Pontos ka, Pontos kd, Pontos ke)
    {
        List<NoAET> lista;
        Pontos cor;
        double z, inczx;
        int y = 0;
        AET aet = new AET(), aetAux;
        while (y < et.size() && et.get(y) == null) {
            y++;
        }
        do 
        {
            if (et.get(y) != null) {
                aet.add(et.get(y).getLista());
            }
            aetAux = new AET();
            for (NoAET no : aet.getLista()) {
                if (no.getYmax() > y) {
                    aetAux.add(no);
                }
            }
            aet = aetAux;
            aet.ordena();
            lista = aet.getLista();
            for (int i = 0, x, x2; i < lista.size() - 1; i += 2) 
            {
                x = (int)Math.round(lista.get(i).getXmin());
                x2 = (int)Math.round(lista.get(i + 1).getXmin());
                z = lista.get(i).getZmin();
                double r, g, b, incrx, incgx, incbx, dx = x2 - x;
                r = lista.get(i).getRxmin();
                g = lista.get(i).getGymin();
                b = lista.get(i).getBzmin();
                incrx = (lista.get(i + 1).getRxmin() - lista.get(i).getRxmin()) / dx;
                incgx = (lista.get(i + 1).getGymin() - lista.get(i).getGymin()) / dx;
                incbx = (lista.get(i + 1).getBzmin() - lista.get(i).getBzmin()) / dx;
                inczx = (lista.get(i + 1).getZmin() - lista.get(i).getZmin()) / dx;
                
                while (x <= x2) 
                {
                    if (inImage(gc, x, y) && z > zbuffer[x][y]) 
                    {
                        zbuffer[x][y] = z;
                        cor = obj.corPhong(Luz, Eye, new Pontos(r, g, b), n, ia, id, ie, ka, kd, ke);
                        Color c = Color.rgb((int)cor.getX(), (int)cor.getY(), (int)cor.getZ());
                        writePixel(x, y, gc, c);
                    }
                    r += incrx;
                    g += incgx;
                    b += incbx;
                    z += inczx;
                    x++;
                }
            }
            for (NoAET noAET : aet.getLista()) {
                noAET.setXmin(noAET.getXmin() + noAET.getIncx());
                noAET.setZmin(noAET.getZmin() + noAET.getInczy());
                noAET.setRxmin(noAET.getRxmin()+ noAET.getIncrx());
                noAET.setGymin(noAET.getGymin()+ noAET.getIncgy());
                noAET.setBzmin(noAET.getBzmin() + noAET.getIncbz());
            }
            y++;
        } while (aet.getLista().size() > 0);
    }
    
    @FXML
    private void clkRbPhong(ActionEvent event) {
        redesenha(obj, canvas.getGraphicsContext2D());
    }

    @FXML
    private void clkRbFlat(ActionEvent event) {
        redesenha(obj, canvas.getGraphicsContext2D());
    }

    @FXML
    private void clkRbGouraud(ActionEvent event) {
        redesenha(obj, canvas.getGraphicsContext2D());
    }
    
    private void attPVermelho(double valor)
    {
        if(rbAmbiente.isSelected())
            ka.setX(valor);
        if(rbEspecular.isSelected())
            ke.setX(valor);
        if(rbDifusa.isSelected())
            kd.setX(valor);
    }
    
    private void attPVerde(double valor)
    {
        if(rbAmbiente.isSelected())
            ka.setY(valor);
        if(rbEspecular.isSelected())
            ke.setY(valor);
        if(rbDifusa.isSelected())
            kd.setY(valor);
    }
    
    private void attPAzul(double valor)
    {
        if(rbAmbiente.isSelected())
            ka.setZ(valor);
        if(rbEspecular.isSelected())
            ke.setZ(valor);
        if(rbDifusa.isSelected())
            kd.setZ(valor);
    }
    
    private void exibe_coloracao(Pontos p)
    {
        sldVermelho.setValue(p.getX() * 255);
        sldVerde.setValue(p.getY() * 255);
        sldAzul.setValue(p.getZ() * 255);
    }
    
   
    @FXML
    private void clkRbAmbiente(ActionEvent event) {
        exibe_coloracao(ka);
    }

    @FXML
    private void clkRbEspecular(ActionEvent event) {
        exibe_coloracao(kd);
    }

    @FXML
    private void clkRbDifusa(ActionEvent event) {
        exibe_coloracao(ke);
    }

    @FXML
    private void clkCor(ActionEvent event) {
        kd.setX(corPicker.getValue().getRed());
        kd.setY(corPicker.getValue().getGreen());
        kd.setZ(corPicker.getValue().getBlue());
        exibe_coloracao(kd);
        redesenha(obj, canvas.getGraphicsContext2D());
    }

    @FXML
    private void mvVermelho(MouseEvent event) {
        attPVermelho(sldVermelho.getValue()/255);
    }

    @FXML
    private void mvVerde(MouseEvent event) {
        attPVerde(sldVerde.getValue()/255);
    }

    @FXML
    private void mvAzul(MouseEvent event) {
        attPAzul(sldAzul.getValue()/255);
    }

    @FXML
    private void clkrbSolido(ActionEvent event) {
        rbFlat.setSelected(true);
        redesenha(obj, canvas.getGraphicsContext2D());
    }

    @FXML
    private void clkrbAramado(ActionEvent event) {
        redesenha(obj, canvas.getGraphicsContext2D());
    }
    
    
}
