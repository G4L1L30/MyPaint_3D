/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypaint3d;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author g4l1l3u
 */
public class Objeto {
    
    private List<Pontos> listVertOriginal, listVertAtual; //Aramado
    private List<List> faces, listaFacesVertices; //Solido
    private Pontos[] vetNfaces, vetNvertices; //Solido
    
    private double[][] MA;
    private int qtdeFaces, qtdeVert;
    
    
    private void inicializa()
    {
        listVertOriginal = new ArrayList<>();
        listVertAtual = new ArrayList<>();
        faces = new ArrayList<>();
        vetNfaces = vetNvertices = null;
        listaFacesVertices = new ArrayList<>();
        MA = matrizIdentidade();
        qtdeFaces = 0;
        qtdeVert = 0;
    }
    
    private double[][] matrizIdentidade()
    {
        double[][] mat = new double[4][4];
        mat[0][0] = 1;
        mat[1][1] = 1;
        mat[2][2] = 1;
        mat[3][3] = 1;
        return mat;
    }
    
    private double[][] copiaMatriz(double[][] ma, int l, int c)
    {
        double[][] aux = new double[l][c];
        for(int i = 0; i < l; i++)
            for(int j = 0; j < c; j++)
                aux[i][j] = ma[i][j];
        return aux;
    }
    
    private void multiplicaMatriz(double[][] mat)
    {
        double soma;
        double[][] mataux = copiaMatriz(MA, 4, 4);
        for(int l = 0; l < 4; l++)
        {
            for(int c = 0; c < 4; c++)
            {
                soma = 0;
                for(int i = 0; i < 4; i++)
                    soma +=  mat[l][i] * MA[i][c];
                mataux[l][c] = soma;
            }
        }
        MA = mataux;    
    }
    
    private double[][] multiplicaPontos(double[][] mat)
    {
        double soma;
        double[][] mataux = copiaMatriz(mat, 4, 1);
        for(int l = 0; l < 4; l++)
        {
            for(int c = 0; c < 1; c++)
            {
                soma = 0;
                for(int i = 0; i < 4; i++)
                    soma += MA[l][i] * mat[i][c];
                mataux[l][c] = soma;
            }
        }
        return mataux;
    }
    
    public Pontos centroPoligno()
    {
        double x = 0.0, y = 0.0, z = 0.0;
        
        for(int i = 0; i < listVertAtual.size(); i++)
        {
            x += listVertAtual.get(i).getX();
            y += listVertAtual.get(i).getY();
            z += listVertAtual.get(i).getZ();
        }
        x /= listVertAtual.size();
        y /= listVertAtual.size();
        z /= listVertAtual.size();
        return new Pontos(x, y, z);
    }
    
    private void atualizaPontos()
    {
        for(int i = 0; i <listVertOriginal.size(); i++)
        {
            double[][] ponto = new double[4][1];
            ponto[0][0] = listVertOriginal.get(i).getX();
            ponto[1][0] = listVertOriginal.get(i).getY();
            ponto[2][0] = listVertOriginal.get(i).getZ();
            ponto[3][0] = 1;
            ponto = multiplicaPontos(ponto);
            listVertAtual.set(i, new Pontos(ponto[0][0], ponto[1][0], ponto[2][0]));
        }
    }
    
    //OK
    public void translacao(double x, double y, double z)
    {
        double[][] matTrans = matrizIdentidade();
        matTrans[0][3] = x;
        matTrans[1][3] = y;
        matTrans[2][3] = z;
        multiplicaMatriz(matTrans);
        atualizaPontos();
    }
    
    //OK
    public void escala(double x, double y, double z)
    {
        Pontos centro = centroPoligno();
        double[][] matEscala = matrizIdentidade();
        matEscala[0][0] = x;
        matEscala[1][1] = y;
        matEscala[2][2] = z;
        translacao(-centro.getX(), -centro.getY(), -centro.getZ());
        multiplicaMatriz(matEscala);
        translacao(centro.getX(), centro.getY(), centro.getZ());
        atualizaPontos();
    }
    
    //OK
    public void rotacaoX(double angulo, boolean faceOculta)
    {
        Pontos centro = centroPoligno();
        double rad = angulo * Math.PI / 180.0;
        double[][] matRot = matrizIdentidade();
        matRot[1][1] = Math.cos(rad);
        matRot[1][2] = -Math.sin(rad);
        matRot[2][1] = Math.sin(rad);
        matRot[2][2] = Math.cos(rad);
        translacao(-centro.getX(), -centro.getY(), -centro.getZ());
        multiplicaMatriz(matRot);
        translacao(centro.getX(), centro.getY(), centro.getZ());
        atualizaPontos();
        if(faceOculta)
            backFaceCulling();
    }
    
    //OK
    public void rotacaoY(double angulo, boolean faceOculta)
    {
        Pontos centro = centroPoligno();
        double rad = angulo * Math.PI / 180.0;
        double[][] matRot = matrizIdentidade();
        matRot[0][0] = Math.cos(rad);
        matRot[0][2] = Math.sin(rad);
        matRot[2][0] = -Math.sin(rad);
        matRot[2][2] = Math.cos(rad);
        translacao(-centro.getX(), -centro.getY(), -centro.getZ());
        multiplicaMatriz(matRot);
        translacao(centro.getX(), centro.getY(), centro.getZ());
        atualizaPontos();
        if(faceOculta)
            backFaceCulling();
    }
    
    //OK
    public void rotacaoZ(double angulo, boolean faceOculta)
    {
        Pontos centro = centroPoligno();
        double rad = angulo * Math.PI / 180.0;
        double[][] matRot = matrizIdentidade();
        matRot[0][0] = Math.cos(rad);
        matRot[0][1] = -Math.sin(rad);
        matRot[1][0] = Math.sin(rad);
        matRot[1][1] = Math.cos(rad);
        translacao(-centro.getX(), -centro.getY(), -centro.getZ());
        multiplicaMatriz(matRot);
        translacao(centro.getX(), centro.getY(), centro.getZ());
        atualizaPontos();
        if(faceOculta)
            backFaceCulling();
    }
    
    public Objeto(File arquivo, boolean faceOculta)
    {
        inicializa();
        lerArquivo(arquivo, faceOculta);
        
    }
    
    private void addVertices(Pontos pt)
    {
        listVertAtual.add(pt);
        listVertOriginal.add(pt);
    }
    
    private void addFace(List face)
    {
        faces.add(face);
    }
    
    private void lerArquivo(File arquivo, boolean faceOculta)
    {
        String linha;
        String[] partes, fc;
        double x, y, z;
        int indice;
        List face;
        try{
            BufferedReader bf = new BufferedReader(new FileReader(arquivo));
            try {
                linha = bf.readLine();
                while(linha != null)
                {
                    linha = linha.trim();
                    partes = linha.split(" ");
                    if(partes[0].equals("v")) //Vertices
                    {
                        x = Double.parseDouble(partes[1].replace(",", "."));
                        y = Double.parseDouble(partes[2].replace(",", "."));
                        z = Double.parseDouble(partes[3].replace(",", "."));
                        addVertices(new Pontos(x, y, z)); //Adiciona na lista de Vertices originais e atuais;
                        listaFacesVertices.add(new ArrayList<>());
                        qtdeVert++;
                    }
                    else
                    {
                        if(partes[0].equals("f")) //Faces
                        {
                            face = new ArrayList<>();
                            for(int i = 1; i < partes.length; i++)
                            {
                                fc = partes[i].split("/");
                                indice = Integer.parseInt(fc[0])-1;
                                face.add(indice);
                                listaFacesVertices.get(indice).add(faces.size());
                            }
                            addFace(face);
                            qtdeFaces++;
                        }
                    }
                    linha = bf.readLine();
                }
                bf.close();
                if(faceOculta)
                    backFaceCulling();
            } catch (IOException ex) {
                Logger.getLogger(Objeto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    
    public int[] qtde()
    {
        int qt[] = new int[2];
        qt[0] = qtdeFaces;
        qt[1] = qtdeVert;
        return qt;
    }
    
    public List<List> getListaFaces() {
        return faces;
    }
    
    public Pontos getListFacesPos(int i, int a)
    {
        return new Pontos(listVertAtual.get((int)faces.get(i).get(a)).getX(), listVertAtual.get((int)faces.get(i).get(a)).getY(), listVertAtual.get((int)faces.get(i).get(a)).getZ());
    }
    
    private void initNormais()
    {
        vetNfaces = new Pontos[faces.size()];
        vetNvertices = new Pontos[listVertOriginal.size()];
    }
    
    public void backFaceCulling()
    {
        Pontos novo;
        initNormais();
        for(int i = 0; i < faces.size(); i++)
        {
            novo = vetNormalFace(faces.get(i));
            novo = novo.normalizar();
            vetNfaces[i] = novo;
        }
    }
    
    private Pontos vetNormalFace(List face)
    {
        Pontos a, b, n, ab, an;
        a = listVertAtual.get((int)face.get(0));
        b = listVertAtual.get((int)face.get(1));
        n = listVertAtual.get((int)face.get(face.size()-1));
        ab = b.menos(a);
        an = n.menos(a);
        return ab.produto_Vetorial(an);
    }
    
    public Pontos getVNFace(int pos)
    {
        return vetNfaces[pos];
    }
    
    public void atualizaVetNormaisVertices()
    {
        for(int i = 0; i < vetNvertices.length; i++)
        {
            List<Pontos> normais = new ArrayList<>();
            for(Object k : listaFacesVertices.get(i))
                normais.add(vetNfaces[(int)k]);
            vetNvertices[i] = mediaVetNormais(normais);
        }
        
    }
    
    private Pontos mediaVetNormais(List<Pontos> normais)
    {
        double x, y, z, tam = normais.size();
        x = y = z = 0;
        for(Pontos v : normais)
        {
            x += v.getX();
            y += v.getY();
            z += v.getZ();
        }
        return new Pontos(x/tam, y/tam, z/tam);
    }
    
    public ET gerarETFaceFlat(int f, int height, int tx, int ty, Pontos Luz, Pontos Eye, int n,
            Pontos ia, Pontos id, Pontos ie, Pontos ka, Pontos kd, Pontos ke){
        
        Pontos cor;
        ET et = new ET(height + 1);
        double xmax, ymax, zmax, xmin, ymin, zmin, dx, dy, dz;
        double incx, incz;
        int y;
        List face = faces.get(f);
        cor = corPhong(Luz, Eye, vetNfaces[f], n, ia, id, ie, ka, kd, ke);
        for(int i = 0; i < face.size() - 1; i++)
        {
            if(listVertAtual.get((int)face.get(i)).getY() >= listVertAtual.get((int)face.get(i+1)).getY())
            {
                xmax = listVertAtual.get((int)face.get(i)).getX();
                ymax = listVertAtual.get((int)face.get(i)).getY();
                zmax = listVertAtual.get((int)face.get(i)).getZ();
                xmin = listVertAtual.get((int)face.get(i+1)).getX();
                ymin = listVertAtual.get((int)face.get(i+1)).getY();
                zmin = listVertAtual.get((int)face.get(i+1)).getZ();
            }
            else
            {
                xmin = listVertAtual.get((int)face.get(i)).getX();
                ymin = listVertAtual.get((int)face.get(i)).getY();
                zmin = listVertAtual.get((int)face.get(i)).getZ();
                xmax = listVertAtual.get((int)face.get(i+1)).getX();
                ymax = listVertAtual.get((int)face.get(i+1)).getY();
                zmax = listVertAtual.get((int)face.get(i+1)).getZ();
            }
            dx = xmax - xmin;
            dy = ymax - ymin;
            dz = zmax - zmin;
            
            incx = (dy != 0) ? dx / dy : 0;
            incz = (dy != 0) ? dz / dy : 0;
            y = (int)ymin + ty;
            if(y < 0) 
                y = 0;
            else 
                if(y >= height) 
                    y = height - 1;
            if(et.get(y) == null)
                et.init(y);
            et.get(y).add(new NoAET((int)ymax + ty, xmin + tx, incx, zmin, incz, cor.getX(), cor.getY(), cor.getZ(), 0, 0, 0));
        }
        
        if(listVertAtual.get((int)face.get(0)).getY() >= listVertAtual.get((int)face.get(face.size() - 1)).getY())
        {
            xmax = listVertAtual.get((int)face.get(0)).getX();
            ymax = listVertAtual.get((int)face.get(0)).getY();
            zmax = listVertAtual.get((int)face.get(0)).getZ();
            xmin = listVertAtual.get((int)face.get(face.size() - 1)).getX();
            ymin = listVertAtual.get((int)face.get(face.size() - 1)).getY();
            zmin = listVertAtual.get((int)face.get(face.size() - 1)).getZ();
        }
        else
        {
            xmin = listVertAtual.get((int)face.get(0)).getX();
            ymin = listVertAtual.get((int)face.get(0)).getY();
            zmin = listVertAtual.get((int)face.get(0)).getZ();
            xmax = listVertAtual.get((int)face.get(face.size() - 1)).getX();
            ymax = listVertAtual.get((int)face.get(face.size() - 1)).getY();
            zmax = listVertAtual.get((int)face.get(face.size() - 1)).getZ();
        }
        dx = xmax - xmin;
        dy = ymax - ymin;
        dz = zmax - zmin;

        incx = (dy != 0) ? dx / dy : 0;
        incz = (dy != 0) ? dz / dy : 0;
        y = (int)ymin + ty;
        if(y < 0) 
            y = 0;
        else 
            if(y >= height) 
                y = height - 1;
        if(et.get(y) == null)
            et.init(y);
        et.get(y).add(new NoAET((int)ymax + ty, xmin + tx, incx, zmin, incz, cor.getX(), cor.getY(), cor.getZ(), 0, 0, 0));
        return et;
    }
    
    public ET gerarETFaceGourand(int f, int height, int tx, int ty, Pontos Luz, Pontos Eye, int n,
            Pontos ia, Pontos id, Pontos ie, Pontos ka, Pontos kd, Pontos ke){
        
        Pontos cl, cm;
        ET et = new ET(height + 1);
        double xmax, ymax, zmax, xmin, ymin, zmin, dx, dy, dz;
        double incx, incz, incrx, incgy, incbz;
        int y;
        List face = faces.get(f);
        for(int i = 0; i < face.size() - 1; i++)
        {
            if(listVertAtual.get((int)face.get(i)).getY() >= listVertAtual.get((int)face.get(i+1)).getY())
            {
                xmax = listVertAtual.get((int)face.get(i)).getX();
                ymax = listVertAtual.get((int)face.get(i)).getY();
                zmax = listVertAtual.get((int)face.get(i)).getZ();
                cm = corPhong(Luz, Eye, vetNvertices[(int)face.get(i)], n, ia, id, ie, ka, kd, ke);
                xmin = listVertAtual.get((int)face.get(i+1)).getX();
                ymin = listVertAtual.get((int)face.get(i+1)).getY();
                zmin = listVertAtual.get((int)face.get(i+1)).getZ();
                cl = corPhong(Luz, Eye, vetNvertices[(int)face.get(i+1)], n, ia, id, ie, ka, kd, ke);
            }
            else
            {
                xmin = listVertAtual.get((int)face.get(i)).getX();
                ymin = listVertAtual.get((int)face.get(i)).getY();
                zmin = listVertAtual.get((int)face.get(i)).getZ();
                cl = corPhong(Luz, Eye, vetNvertices[(int)face.get(i)], n, ia, id, ie, ka, kd, ke);
                xmax = listVertAtual.get((int)face.get(i+1)).getX();
                ymax = listVertAtual.get((int)face.get(i+1)).getY();
                zmax = listVertAtual.get((int)face.get(i+1)).getZ();
                cm = corPhong(Luz, Eye, vetNvertices[(int)face.get(i+1)], n, ia, id, ie, ka, kd, ke);
            }
            dx = xmax - xmin;
            dy = ymax - ymin;
            dz = zmax - zmin;
            
            incx = (dy != 0) ? dx / dy : 0;
            incz = (dy != 0) ? dz / dy : 0;
            incrx = (cm.getX() - cl.getX()) / dy;
            incgy = (cm.getY() - cl.getY()) / dy;
            incbz = (cm.getZ() - cl.getZ()) / dy;
            y = (int)ymin + ty;
            if(y < 0) 
                y = 0;
            else 
                if(y >= height) 
                    y = height - 1;
            if(et.get(y) == null)
                et.init(y);
            et.get(y).add(new NoAET((int)ymax + ty, xmin + tx, incx, zmin, incz, cl.getX(), cl.getY(), cl.getZ(), incrx, incgy, incbz));
        }
        
        if(listVertAtual.get((int)face.get(0)).getY() >= listVertAtual.get((int)face.get(face.size() - 1)).getY())
        {
            xmax = listVertAtual.get((int)face.get(0)).getX();
            ymax = listVertAtual.get((int)face.get(0)).getY();
            zmax = listVertAtual.get((int)face.get(0)).getZ();
            cm = corPhong(Luz, Eye, vetNvertices[(int)face.get(0)], n, ia, id, ie, ka, kd, ke);
            xmin = listVertAtual.get((int)face.get(face.size() - 1)).getX();
            ymin = listVertAtual.get((int)face.get(face.size() - 1)).getY();
            zmin = listVertAtual.get((int)face.get(face.size() - 1)).getZ();
            cl = corPhong(Luz, Eye, vetNvertices[(int)face.get(face.size() - 1)], n, ia, id, ie, ka, kd, ke);
        }
        else
        {
            xmin = listVertAtual.get((int)face.get(0)).getX();
            ymin = listVertAtual.get((int)face.get(0)).getY();
            zmin = listVertAtual.get((int)face.get(0)).getZ();
            cl = corPhong(Luz, Eye, vetNvertices[(int)face.get(0)], n, ia, id, ie, ka, kd, ke);
            xmax = listVertAtual.get((int)face.get(face.size() - 1)).getX();
            ymax = listVertAtual.get((int)face.get(face.size() - 1)).getY();
            zmax = listVertAtual.get((int)face.get(face.size() - 1)).getZ();
            cm = corPhong(Luz, Eye, vetNvertices[(int)face.get(face.size() - 1)], n, ia, id, ie, ka, kd, ke);
        }
        dx = xmax - xmin;
        dy = ymax - ymin;
        dz = zmax - zmin;

        incx = (dy != 0) ? dx / dy : 0;
        incz = (dy != 0) ? dz / dy : 0;
        incrx = (cm.getX() - cl.getX()) / dy;
        incgy = (cm.getY() - cl.getY()) / dy;
        incbz = (cm.getZ() - cl.getZ()) / dy;
        y = (int)ymin + ty;
        if(y < 0) 
            y = 0;
        else 
            if(y >= height) 
                y = height - 1;
        if(et.get(y) == null)
            et.init(y);
        et.get(y).add(new NoAET((int)ymax + ty, xmin + tx, incx, zmin, incz, cl.getX(), cl.getY(), cl.getZ(), incrx, incgy, incbz));
        return et;
    }
    
    
    public ET gerarETFacePhong(int nf, int height, int tx, int ty)
    {
        ET et = new ET(height + 1);
        double xmax, ymax, zmax, xmin, ymin, zmin, dx, dy, dz, rl, rm, gl, gm, bl, bm;
        double incx, incz, incrx, incgy, incbz;
        int y;
        List face = faces.get(nf);
        for (int i = 0; i < face.size()-1; i++)
        {
            if(listVertAtual.get((int)face.get(i)).getY() >= listVertAtual.get((int)face.get(i+1)).getY())
            {
                xmax = listVertAtual.get((int)face.get(i)).getX();
                ymax = listVertAtual.get((int)face.get(i)).getY();
                zmax = listVertAtual.get((int)face.get(i)).getZ();
                rm = vetNvertices[(int)face.get(i)].getX();
                gm = vetNvertices[(int)face.get(i)].getY();
                bm = vetNvertices[(int)face.get(i)].getZ();
                xmin = listVertAtual.get((int)face.get(i+1)).getX();
                ymin = listVertAtual.get((int)face.get(i+1)).getY();
                zmin = listVertAtual.get((int)face.get(i+1)).getZ();
                rl = vetNvertices[(int)face.get(i+1)].getX();
                gl = vetNvertices[(int)face.get(i+1)].getY();
                bl = vetNvertices[(int)face.get(i+1)].getZ();

            }
            else
            {
                xmin = listVertAtual.get((int)face.get(i)).getX();
                ymin = listVertAtual.get((int)face.get(i)).getY();
                zmin = listVertAtual.get((int)face.get(i)).getZ();
                rl = vetNvertices[(int)face.get(i)].getX();
                gl = vetNvertices[(int)face.get(i)].getY();
                bl = vetNvertices[(int)face.get(i)].getZ();
                xmax = listVertAtual.get((int)face.get(i+1)).getX();
                ymax = listVertAtual.get((int)face.get(i+1)).getY();
                zmax = listVertAtual.get((int)face.get(i+1)).getZ();
                rm = vetNvertices[(int)face.get(i+1)].getX();
                gm = vetNvertices[(int)face.get(i+1)].getY();
                bm = vetNvertices[(int)face.get(i+1)].getZ();
            }
            dx = xmax - xmin;
            dy = ymax - ymin;
            dz = zmax - zmin;

            incx = (dy != 0) ? dx / dy : 0;
            incz = (dy != 0) ? dz / dy : 0;
            incrx = (rm - rl) / dy;
            incgy = (gm - gl) / dy;
            incbz = (bm - bl) / dy;

            y = (int)ymin + ty;
            if (y < 0) 
                y = 0;
            else 
                if(y >= height) 
                    y = height - 1;
            if(et.get(y) == null)
                et.init(y);
            et.get(y).add(new NoAET((int)ymax + ty, xmin + tx, incx, zmin, incz, rl, gl, bl, incrx, incgy, incbz));
        }
        if(listVertAtual.get((int)face.get(0)).getY() >= listVertAtual.get((int)face.get(face.size()-1)).getY())
        {
            xmax = listVertAtual.get((int)face.get(0)).getX();
            ymax = listVertAtual.get((int)face.get(0)).getY();
            zmax = listVertAtual.get((int)face.get(0)).getZ();
            rm = vetNvertices[(int)face.get(0)].getX();
            gm = vetNvertices[(int)face.get(0)].getY();
            bm = vetNvertices[(int)face.get(0)].getZ();
            xmin = listVertAtual.get((int)face.get(face.size()-1)).getX();
            ymin = listVertAtual.get((int)face.get(face.size()-1)).getY();
            zmin = listVertAtual.get((int)face.get(face.size()-1)).getZ();
            rl = vetNvertices[(int)face.get(face.size()-1)].getX();
            gl = vetNvertices[(int)face.get(face.size()-1)].getY();
            bl = vetNvertices[(int)face.get(face.size()-1)].getZ();
        }
        else
        {
            xmin = listVertAtual.get((int)face.get(0)).getX();
            ymin = listVertAtual.get((int)face.get(0)).getY();
            zmin = listVertAtual.get((int)face.get(0)).getZ();
            rl = vetNvertices[(int)face.get(0)].getX();
            gl = vetNvertices[(int)face.get(0)].getY();
            bl = vetNvertices[(int)face.get(0)].getZ();
            xmax = listVertAtual.get((int)face.get(face.size()-1)).getX();
            ymax = listVertAtual.get((int)face.get(face.size()-1)).getY();
            zmax = listVertAtual.get((int)face.get(face.size()-1)).getZ();
            rm = vetNvertices[(int)face.get(face.size()-1)].getX();
            gm = vetNvertices[(int)face.get(face.size()-1)].getY();
            bm = vetNvertices[(int)face.get(face.size()-1)].getZ();
        }
        dx = xmax - xmin;
        dy = ymax - ymin;
        dz = zmax - zmin;

        incx = (dy != 0) ? dx / dy : 0;
        incz = (dy != 0) ? dz / dy : 0;
        incrx = (rm - rl) / dy;
        incgy = (gm - gl) / dy;
        incbz = (bm - bl) / dy;

        y = (int)ymin + ty;
        if (y < 0) 
            y = 0;
        else 
            if (y >= height) 
                y = height - 1;
        if (et.get(y) == null)
            et.init(y);
        et.get(y).add(new NoAET((int)ymax + ty, xmin + tx, incx, zmin, incz, rl, gl, bl, incrx, incgy, incbz));
        return et;
    }
    
    public Pontos corPhong(Pontos Luz, Pontos Eye, Pontos N, int n, Pontos ia, Pontos id, Pontos ie, Pontos ka, Pontos kd, Pontos ke)
    {
        Pontos H = Luz.mais(Eye).normalizar();
        double hnn = Math.pow(H.produto_Escalar(N), n), ln = Luz.produto_Escalar(N);

        double r = ia.getX() * ka.getX() + id.getX() * kd.getX() * ln + ie.getX() * ke.getX() * hnn;
        double g = ia.getY() * ka.getY() + id.getY() * kd.getY() * ln + ie.getY() * ke.getY() * hnn;
        double b = ia.getZ() * ka.getZ() + id.getZ() * kd.getZ() * ln + ie.getZ() * ke.getZ() * hnn;
        r = r < 0 ? 0 : (r > 1 ? 1 : r);
        g = g < 0 ? 0 : (g > 1 ? 1 : g);
        b = b < 0 ? 0 : (b > 1 ? 1 : b);
        return new Pontos(r * 255, g * 255, b * 255);
    }
    
    /*
    private void exibe()
    {
        for(int i = 0; i < listVertAtual.size(); i++)
            System.out.println(listVertAtual.get(i).getX() + " " + listVertAtual.get(i).getY() + " " + listVertAtual.get(i).getZ());
    }
    
    private void exibeF()
    {
        for (int i = 0; i < faces.size(); i++)
        {
            for(int a = 0; a < faces.get(i).size(); a++)
            {
                System.out.print(faces.get(i).get(a) + " ");
            }
            System.out.println("");
        }
    }
    */
}
