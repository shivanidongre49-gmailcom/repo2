package helloworld;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Info {

   public String id;
    public String FirstName;
    public String LastName;
   // public String s3Url;
    public String name;
    public String contentType;
    public String s3Uri;
    public Long sizeBytes;
    public  String bucket;
    public String lastModified;
public  String policy;

    public Info(String id, String firstName,String bucket, String lastName, String name, String contentType, String s3Uri, Long sizeBytes, String lastModified, String policy) {
        this.id = id;
        FirstName = firstName;
        LastName = lastName;
this.bucket=bucket;
        this.name = name;
        this.contentType = contentType;
        this.s3Uri = s3Uri;
        this.sizeBytes = sizeBytes;
        this.lastModified = lastModified;
        this.policy = policy;
    }
    //    public Info(String s3Url) {
//    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getS3Uri() {
        return s3Uri;
    }

    public void setS3Uri(String s3Uri) {
        this.s3Uri = s3Uri;
    }

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
}
