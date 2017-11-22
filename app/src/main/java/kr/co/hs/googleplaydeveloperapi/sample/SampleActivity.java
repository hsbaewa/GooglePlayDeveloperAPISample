package kr.co.hs.googleplaydeveloperapi.sample;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.model.AppEdit;
import com.google.api.services.androidpublisher.model.Track;

import java.util.Collections;
import java.util.List;

import kr.co.hs.googleplaydeveloperapi.AndroidPublisherHelper;

import static java.security.AccessController.getContext;

/**
 * Created by privacydev on 2017. 11. 22..
 */

public class SampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            String packageName = getPackageName();
            //3번쨰 파라미터에 play console -> 설정 -> API 액세스 -> 서비스 계정 에 있는 이메일 명을 집어 넣으면 됨.
            AndroidPublisher publisher = AndroidPublisherHelper.init(this, packageName ,"서비스 계정 이름");
            AndroidPublisher.Edits edits = publisher.edits();
            AndroidPublisher.Edits.Insert editRequest = edits.insert(packageName, null);
            AppEdit appEdit = editRequest.execute();
            String editId = appEdit.getId();

//							Track track = edits.tracks().get(packageName, editId, "alpha").execute();
            Track track = edits.tracks().get(packageName, editId, "producton").execute();

            String trac = track.getTrack();
            List<Integer> versioIntegers = track.getVersionCodes();
            Collections.sort(versioIntegers);
            int maxVersion = versioIntegers.get(versioIntegers.size()-1);

            PackageInfo packageInfo = getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
            int currentVersion = packageInfo.versionCode;
//            if(maxVersion > currentVersion)

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
