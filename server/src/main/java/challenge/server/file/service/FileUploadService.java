package challenge.server.file.service;

import challenge.server.exception.BusinessLogicException;
import challenge.server.exception.ExceptionCode;
import challenge.server.file.dto.FileDto;
import challenge.server.file.storage.AmazonS3ResourceStorage;
import challenge.server.file.util.MultipartUtil;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkBaseException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadService {

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    /**
     * 요청을 받은 MultipartFile 객체에서 파일의 핵심 속성을 골라서 FileDto 객체를 만들어준다.
     * 실제 물리적인 File은 업로드 처리를 하고 DTO를 반환한다.
     */
    public String save(MultipartFile multipartFile) {
//        FileDto fileDto = FileDto.multipartOf(multipartFile);
        String path = MultipartUtil.createPath(multipartFile);

        return amazonS3ResourceStorage.store(path, multipartFile);
    }

    public void delete(String fileUrl) throws SdkBaseException {
        amazonS3ResourceStorage.delete(fileUrl);
    }
}
