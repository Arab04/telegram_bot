package fabrico.nova.optics.service.image;

import fabrico.nova.optics.model.ImageEntity;

public interface ImageService {

    ImageEntity saveImage(ImageEntity image);


    ImageEntity getImage(String phoneNumber);
}
