package edu.niptict.covid19.ui.checkup;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";

    private final MutableLiveData<Boolean> mIsCheckingUp;

    private final LiveData<CheckUpResult> mLastResult;

    public HomeViewModel() {
        mLastResult = new MutableLiveData<>(new CheckUpResult(3, 5, "ត្រូវបំពេញជាតិទឹក ដោយហូបទឹកឱ្យបានច្រើន ៣ទៅ៤លីត្រក្នុងមួយថ្ងៃ ថែបំប៉នសុខភាព ហើយត្រូវអនុវត្តន៍តាម វិធានការ ការពារអនាម័យ ឱ្យបានខ្ចាប់ខ្ចួន ធ្វើការតាមដានបន្ត និងត្រូវធ្វើការ វាយតំលៃឡើងវីញម្ដងទៀតនៅ ២ថ្ងៃក្រោយ(រៀងរាល់២ថ្ងៃម្ដង) និងត្រូវរក្សារគំលាតពីសមាជិកក្បែរខ្លួន រយៈពេល១៤ថ្ងៃ។"));
        mIsCheckingUp = new MutableLiveData<>(false);
    }

    public void setCheckUp(boolean checking) {
        Log.i(TAG, "setCheckUp: " + checking);

        Boolean value = mIsCheckingUp.getValue();
        if (value == null || value != checking) {
            mIsCheckingUp.setValue(checking);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters
    ///////////////////////////////////////////////////////////////////////////

    LiveData<Boolean> getIsCheckingUp() {
        return mIsCheckingUp;
    }

    public LiveData<CheckUpResult> getLastResult() {
        return mLastResult;
    }
}