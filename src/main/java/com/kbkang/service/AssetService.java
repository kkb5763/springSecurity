import java.util.List;

public interface AssetService {
    List<Asset> findAll();
    Asset findById(Long id);
    Asset save(Asset asset);
    Asset update(Long id, Asset updatedAsset);
    boolean delete(Long id);
}
