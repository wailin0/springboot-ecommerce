package com.gamingage.component;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Component
public class AWSS3 {

	@Autowired
	private AmazonS3 amazonS3Client;

	@Value("${aws.s3.bucket}")
	private String bucketName;

	public void uploadFile(final String fileName, final MultipartFile file) {

		final ObjectMetadata data = new ObjectMetadata();
		data.setContentType(file.getContentType());
		data.setContentLength(file.getSize());

		try {
			final PutObjectRequest putObjectRequest;

			putObjectRequest = new PutObjectRequest(bucketName, fileName, file.getInputStream(), data)
					.withCannedAcl(CannedAccessControlList.PublicRead);

			amazonS3Client.putObject(putObjectRequest);
		} catch (IOException e) {

		}
	}

}
