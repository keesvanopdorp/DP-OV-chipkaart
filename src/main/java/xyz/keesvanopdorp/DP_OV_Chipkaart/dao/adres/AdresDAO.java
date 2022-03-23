package xyz.keesvanopdorp.DP_OV_Chipkaart.dao.adres;

import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Adres;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Reiziger;

import java.util.ArrayList;
import java.util.List;

public interface AdresDAO {
    public boolean save(Adres inAdres) ;
    public boolean update(Adres inAdres);
    public boolean delete(Adres inAdres);
    public Adres findByReiziger(Reiziger inReiziger);
    public ArrayList<Adres> findAll() ;
}
