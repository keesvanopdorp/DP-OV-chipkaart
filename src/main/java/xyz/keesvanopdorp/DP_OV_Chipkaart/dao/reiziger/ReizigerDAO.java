package xyz.keesvanopdorp.DP_OV_Chipkaart.dao.reiziger;

import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Reiziger;

import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    public boolean save(Reiziger inReiziger) ;
    public boolean update(Reiziger inReiziger);
    public boolean delete(Reiziger inReiziger);
    public Reiziger findById(int id);
    public List<Reiziger> findAll() ;
}
