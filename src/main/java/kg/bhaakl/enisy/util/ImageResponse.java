package kg.bhaakl.enisy.util;

import kg.bhaakl.enisy.models.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageResponse {
    private String uuid;
    private String fileName;
    private String fileType;
    private long size;

    public ImageResponse(Image image) {
        setUuid(image.getUuid());
        setFileName(image.getFileName());
        setFileType(image.getFileType());
        setSize(image.getSize());
    }
}