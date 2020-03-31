package edu.niptict.covid19.ui.checkup;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.common.collect.Collections2;
import com.jommobile.android.jomutils.AppExecutors;
import com.jommobile.android.jomutils.repository.DataBoundResource;
import com.jommobile.android.jomutils.repository.JomRepository;
import com.jommobile.android.jomutils.repository.Resource;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import edu.niptict.covid19.MyApplication;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
public class CheckUpRepository extends JomRepository {

    // https://healthy-cambodia.com/detail/check-coronavirus
    private static final CheckUpQuestion[] sCheckUpQuestions = {
            new CheckUpQuestion("តើអ្នកមាន ក្អក ដែររឺទេ?", 3, 0),
            new CheckUpQuestion("តើអ្នកមាន ជម្ងឺផ្ដាសសាយ ដែររឺទេ?", 1, 1),
            new CheckUpQuestion("តើអ្នកមាន រាគរុស ដែររឺទេ? ", 1, 2),
            new CheckUpQuestion("តើអ្នកមាន ឈឺបំពង់ក ដែររឺទេ?", 1, 3),
            new CheckUpQuestion("តើអ្នកមាន ឈឺខ្លួនប្រាណ ឈឺសាច់ដុំ ឈឺឆ្អឹងឆ្អែង ដែររឺទេ?", 1, 4),
            new CheckUpQuestion("តើអ្នកមាន ឈឺក្បាល ដែររឺទេ?", 1, 5),
            new CheckUpQuestion("តើអ្នកមាន ក្ដៅខ្លួន(កំដៅ ≥ ៣៨ អង្សារ) ដែររឺទេ?", 1, 6),
            new CheckUpQuestion("តើអ្នកមាន ហត់ ពិបាកដកដង្ហើម ដែររឺទេ?", 2, 7),
            new CheckUpQuestion("តើអ្នកមាន អស់កំលាំងខ្លាំង ដែររឺទេ?", 2, 8),
            new CheckUpQuestion("តើអ្នកមាន ធ្វើ​ដំណើរឆ្ងាយ ក្រៅប្រទេស ដែររឺទេ ក្នុងរយៈពេល ១៤ថ្ងៃចុងក្រោយនេះ?", 3, 9),
            new CheckUpQuestion("តើអ្នកមាន ធ្លាប់ធ្វើដំណើរទៅតំបន់ផ្ទុះជម្ងឺកូវីដ១៩ ដែររឺទេ?", 3, 10),
            new CheckUpQuestion("តើអ្នកមាន នៅក្បែរឬប៉ះពាល់ដោយផ្ទាល់ជាមួយអ្នកដែលមានជម្ងឺកូវីដ១៩ ដែររឺទេ?", 3, 11),
    };

    /**
     * minScore inclusive, maxScore exclusive.
     */
    private static final CheckUpResult[] sCheckUpResults = {
            new CheckUpResult(0, 3, "អាចទាក់ទងទៅនិងភាពតានតឹង គិតច្រើន ភ័យបារម្ភជ្រុលដោយខ្លួនឯង និងត្រូវតាមដាននៅផ្ទះ។"),
            new CheckUpResult(3, 6, "ត្រូវបំពេញជាតិទឹក ដោយហូបទឹកឱ្យបានច្រើន ៣ទៅ៤លីត្រក្នុងមួយថ្ងៃ ថែបំប៉នសុខភាព ហើយត្រូវអនុវត្តន៍តាម វិធានការ ការពារអនាម័យ ឱ្យបានខ្ចាប់ខ្ចួន ធ្វើការតាមដានបន្ត និងត្រូវធ្វើការ វាយតំលៃឡើងវីញម្ដងទៀតនៅ ២ថ្ងៃក្រោយ(រៀងរាល់២ថ្ងៃម្ដង) និងត្រូវរក្សារគំលាតពីសមាជិកក្បែរខ្លួន រយៈពេល១៤ថ្ងៃ។"),
            new CheckUpResult(6, 13, "ត្រូវស្វែងរក ការពិគ្រោះយោបល់ ជាមួយវេជ្ជបណ្ឌិត។"),
            new CheckUpResult(13, 21, "ត្រូវទូរសព្ទ័ទៅលេខ ១១៥ ឬលេខទូរស័ព្ទរបស់មន្ទីរសុខាភិបាលខេត្ត-ក្រុង ដែលនៅជិតលោកអ្នកបំផុត(ពេទ្យរដ្ឋ)។")
    };

    public CheckUpRepository(MyApplication app) {
        super(app.getAppExecutors());
    }

    LiveData<Resource<List<CheckUpQuestion>>> loadCheckUpQuestions() {
        final AppExecutors appExecutors = getAppExecutors();
        return new DataBoundResource<List<CheckUpQuestion>>(appExecutors) {
            @NonNull
            @Override
            protected LiveData<List<CheckUpQuestion>> loadFromDb() {
                final MutableLiveData<List<CheckUpQuestion>> result = new MutableLiveData<>();
                // TODO: 3/29/2020 Handler is used for testing loading only. Use actual load result from db instead.
                new Handler().postDelayed(() -> {
                    result.setValue(Arrays.asList(sCheckUpQuestions));
                }, 80);

                return result;
            }
        }.bind().asLiveData();
    }

    LiveData<Resource<CheckUpResult>> loadCheckUpResult(final int score) {
        final AppExecutors appExecutors = getAppExecutors();
        return new DataBoundResource<CheckUpResult>(appExecutors) {
            @NonNull
            @Override
            protected LiveData<CheckUpResult> loadFromDb() {
                final MutableLiveData<CheckUpResult> result = new MutableLiveData<>();

                appExecutors.diskIO().execute(() -> {
                    List<CheckUpResult> checkUpResults = Arrays.asList(sCheckUpResults);
                    Collection<CheckUpResult> filterResult = Collections2.filter(checkUpResults,
                            input -> input.getMinScore() <= score && score < input.getMaxScore());

                    if (filterResult.isEmpty()) {
                        appExecutors.mainThread().execute(() -> result.setValue(null));
                    } else {
                        for (CheckUpResult checkUpResult : filterResult) {
                            appExecutors.mainThread().execute(() -> result.setValue(checkUpResult));
                            break;
                        }
                    }
                });

                return result;
            }
        }.bind().asLiveData();
    }
}
