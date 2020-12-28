/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mypaint3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author g4l1l3u
 */
public class AET {
    
    private final List<NoAET> lista;

    public AET() {
        lista = new ArrayList<>();
    }

    public void add(NoAET no)
    {
        lista.add(no);
    }
    
    public void add(List<NoAET> L)
    {
        for (NoAET n : L) {
            lista.add(n);
        }
    }
    
    public void setLista(List<NoAET> dados)
    {
        lista.addAll(dados);
    }
    
    public void remove(int y)
    {
        for(int i = lista.size()-1; i >= 0; i--)
            if(lista.get(i).getYmax() == y)
                lista.remove(i);
    }
    
    public void ordena()
    {
        //Ordena por Xmin, massa ordenar desa maneira ne! Quer saber como eu fiz
        //Olha ai no código
        Collections.sort(lista);
    }
    
    public List<NoAET> getLista()
    {
        return lista;
    }
    
    public void exibe()
    {
        for(int i = 0; i<lista.size();i++)
            System.out.println(lista.get(i).getXmin());
    }
    
    
    
}
