package edu.niptict.covid19.ui.checkup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jommobile.android.jomutils.repository.Resource;

import java.util.List;

/**
 * This class is used for ...
 *
 * @autor MAO Hieng 3/29/2020
 */
class CheckUpApiService {

    LiveData<Resource<List<CheckUpQuestion>>> getCheckUpQuestions() {
        // TODO: 3/21/2020 awaiting for API
        final MutableLiveData<Resource<List<CheckUpQuestion>>> result = new MutableLiveData<>();
        return result;
    }

}
