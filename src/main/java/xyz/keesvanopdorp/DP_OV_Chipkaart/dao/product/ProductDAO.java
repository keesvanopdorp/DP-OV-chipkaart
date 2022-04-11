package xyz.keesvanopdorp.DP_OV_Chipkaart.dao.product;

import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.OVChipKaart;
import xyz.keesvanopdorp.DP_OV_Chipkaart.domain.Product;

import java.util.ArrayList;

public interface ProductDAO {
    public boolean save(Product inProduct);
    public boolean update(Product inProduct);
    public ArrayList<Product> findAll();
    public Product findById(int id);
    public ArrayList<Product> findByOvchipkaart(OVChipKaart inOvChipKaart);
}
