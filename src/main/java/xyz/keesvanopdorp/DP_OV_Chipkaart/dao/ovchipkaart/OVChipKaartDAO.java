package xyz.keesvanopdorp.DP_OV_Chipkaart.dao.ovchipkaart;

import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.OVChipKaart;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Reiziger;
import java.util.ArrayList;

public interface OVChipKaartDAO {

    public boolean save(OVChipKaart inOVChipkaart);

    public boolean update(OVChipKaart inOVChipkaart);

    public boolean delete(OVChipKaart inOVChipkaart);

    public ArrayList<OVChipKaart> findByReiziger(Reiziger inReiziger);

    public ArrayList<OVChipKaart> findAll();
}
