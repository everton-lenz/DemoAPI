package br.com.sol7.orcamento.version;

import br.com.sol7.orcamento.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class VersionInfo {

    private final String version;
    private final String timestampBuild;
    private final String commitsCount;

    @Autowired
    public VersionInfo(@Value("${application.version}") String version, @Value("${application.build.timestamp}") String timestampBuild, @Value("${application.commitsCount}") String commitsCount) {
        this.version = version;
        this.timestampBuild = timestampBuild;
        this.commitsCount = commitsCount;
    }

    public String getVersion() {
        return version;
    }

    public String getTimestampBuild() {
        return timestampBuild;
        /*try {
            DateFormat format = new SimpleDateFormat("MMddyyHHmmss");
            Date date = format.parse(timestampBuild);

            return DateUtil.getDateAsFormattedString(date);
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
        */
    }

    public String getCommitsCount() {
        return commitsCount;
    }
}