import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    @Override
    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    @Override
    public Asset findById(Long id) {
        return assetRepository.findById(id).orElse(null);
    }

    @Override
    public Asset save(Asset asset) {
        return assetRepository.save(asset);
    }

    @Override
    public Asset update(Long id, Asset updatedAsset) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);
        if (optionalAsset.isPresent()) {
            Asset asset = optionalAsset.get();
            asset.setIp(updatedAsset.getIp());
            asset.setVmHost(updatedAsset.getVmHost());
            asset.setServerType(updatedAsset.getServerType());
            asset.setOs(updatedAsset.getOs());
            asset.setManager(updatedAsset.getManager());
            asset.setInstalledSoftware(updatedAsset.getInstalledSoftware());
            return assetRepository.save(asset);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(Long id) {
        if (assetRepository.existsById(id)) {
            assetRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
