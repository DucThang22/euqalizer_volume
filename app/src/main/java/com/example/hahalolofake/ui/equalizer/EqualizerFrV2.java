package com.example.hahalolofake.ui.equalizer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.example.hahalolofake.R;
import com.example.hahalolofake.data.models.Effect;
import com.example.hahalolofake.data.models.EqualizerModel;
import com.example.hahalolofake.ui.equalizer.adapter.EqualizerAdapter;
import com.example.hahalolofake.ui.equalizer.adapter.EqualizerListener;
import com.example.hahalolofake.ui.preset.PresetAct;
import com.example.hahalolofake.utils.SharePrefs;

public class EqualizerFrV2 extends Fragment {

    public static final String ARG_AUDIO_SESSIOIN_ID = "audio_session_id";

    static int themeColor = Color.parseColor("#FF03DAC5");
    public Equalizer mEqualizer;
    SwitchCompat equalizerSwitch;
    public BassBoost bassBoost;
    public PresetReverb presetReverb;

    int y = 0;

    LineSet dataset;
    //    ImageView spinnerDropDownIcon;
    TextView fragTitle;
    LinearLayout mLinearLayout, mLinearSoundEffect;

    public SeekBar[] seekBarFinal = new SeekBar[5];

    AnalogController bassController, reverbController;

//    Spinner presetSpinner;

    FrameLayout equalizerBlocker;

    LineChartView chart;

    Context ctx;

    public EqualizerFrV2() {
        // Required empty public constructor
    }

    Paint paint;
    float[] points;
    short numberOfFrequencyBands;
    private int audioSesionId;
    static boolean showBackButton = true;

    RecyclerView effectRecycler;

    EqualizerAdapter adapter;

    Vibrator vibrator;

    TextView nameEffect;

    private SharePrefs sharePrefs;

    public static EqualizerFrV2 newInstance(int audioSessionId) {

        Bundle args = new Bundle();
        args.putInt(ARG_AUDIO_SESSIOIN_ID, audioSessionId);

        EqualizerFrV2 fragment = new EqualizerFrV2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Settings.isEditing = true;

        if (getArguments() != null && getArguments().containsKey(ARG_AUDIO_SESSIOIN_ID)) {
            audioSesionId = getArguments().getInt(ARG_AUDIO_SESSIOIN_ID);
        }

        if (Settings.equalizerModel == null) {
            Settings.equalizerModel = new EqualizerModel();
            Settings.equalizerModel.setReverbPreset(PresetReverb.PRESET_NONE);
            Settings.equalizerModel.setBassStrength((short) (1000 / 19));
        }

        mEqualizer = new Equalizer(0, audioSesionId);
        bassBoost = new BassBoost(0, audioSesionId);
        bassBoost.setEnabled(Settings.isEqualizerEnabled);
        BassBoost.Settings bassBoostSettingTemp = bassBoost.getProperties();
        BassBoost.Settings bassBoostSetting = new BassBoost.Settings(bassBoostSettingTemp.toString());
        bassBoostSetting.strength = Settings.equalizerModel.getBassStrength();
        bassBoost.setProperties(bassBoostSetting);

        presetReverb = new PresetReverb(0, audioSesionId);
        presetReverb.setPreset(Settings.equalizerModel.getReverbPreset());
        presetReverb.setEnabled(Settings.isEqualizerEnabled);

        mEqualizer.setEnabled(Settings.isEqualizerEnabled);

        if (Settings.presetPos == 0) {
            for (short bandIdx = 0; bandIdx < mEqualizer.getNumberOfBands(); bandIdx++) {
                mEqualizer.setBandLevel(bandIdx, (short) Settings.seekbarpos[bandIdx]);
            }
        } else {
            mEqualizer.usePreset((short) Settings.presetPos);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ctx = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_equalizer, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharePrefs = new SharePrefs(requireContext());
        equalizerSwitch = view.findViewById(R.id.equalizer_switch);
        equalizerSwitch.setChecked(Settings.isEqualizerEnabled);
        equalizerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("ducthang97", "isChecked: " + isChecked);
                mEqualizer.setEnabled(isChecked);
                bassBoost.setEnabled(isChecked);
                presetReverb.setEnabled(isChecked);
                Settings.isEqualizerEnabled = isChecked;
                Settings.equalizerModel.setEqualizerEnabled(isChecked);

                short numberOfFreqBands = 5;
                for (short j = 0; j < numberOfFreqBands; j++) {
                    if(!isChecked) {
                        seekBarFinal[j].setProgress(0);
                        seekBarFinal[j].setEnabled(false);
                    } else {
                        seekBarFinal[j].setEnabled(true);
                    }
                }
            }
        });

//        spinnerDropDownIcon = view.findViewById(R.id.spinner_dropdown_icon);
//        spinnerDropDownIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                presetSpinner.performClick();
//            }
//        });

//        presetSpinner = view.findViewById(R.id.equalizer_preset_spinner);
//        effectRecycler = view.findViewById(R.id.effectRecycler);

        equalizerBlocker = view.findViewById(R.id.equalizerBlocker);
        nameEffect = view.findViewById(R.id.nameEffect);


//        chart   = view.findViewById(R.id.lineChart);
        paint = new Paint();
        dataset = new LineSet();

        bassController = view.findViewById(R.id.controllerBass);
        reverbController = view.findViewById(R.id.controller3D);

        bassController.setLabel("Bass boost");
        reverbController.setLabel("Visualizer");

        bassController.circlePaint2.setColor(themeColor);
        bassController.linePaint.setColor(themeColor);
        bassController.invalidate();
        reverbController.circlePaint2.setColor(themeColor);
        bassController.linePaint.setColor(themeColor);
        reverbController.invalidate();

        if (!Settings.isEqualizerReloaded) {
            int x = 0;
            if (bassBoost != null) {
                try {
                    x = ((bassBoost.getRoundedStrength() * 19) / 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (presetReverb != null) {
                try {
                    y = (presetReverb.getPreset() * 19) / 6;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (x == 0) {
                bassController.setProgress(1);
            } else {
                bassController.setProgress(x);
            }

            if (y == 0) {
                reverbController.setProgress(1);
            } else {
                reverbController.setProgress(y);
            }
        } else {
            int x = ((Settings.bassStrength * 19) / 1000);
            y = (Settings.reverbPreset * 19) / 6;
            if (x == 0) {
                bassController.setProgress(1);
            } else {
                bassController.setProgress(x);
            }

            if (y == 0) {
                reverbController.setProgress(1);
            } else {
                reverbController.setProgress(y);
            }
        }

        Vibrator vibrator = (Vibrator) requireActivity().getSystemService(Context.VIBRATOR_SERVICE);
        bassController.setOnProgressChangedListener(new AnalogController.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                if (vibrator != null && vibrator.hasVibrator()) {
                    vibrator.vibrate(100);
                }
                Settings.bassStrength = (short) (((float) 1000 / 19) * (progress));
                try {
                    bassBoost.setStrength(Settings.bassStrength);
                    Settings.equalizerModel.setBassStrength(Settings.bassStrength);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        reverbController.setOnProgressChangedListener(new AnalogController.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                if (vibrator != null && vibrator.hasVibrator()) {
                    vibrator.vibrate(100);
                }
                Settings.reverbPreset = (short) ((progress * 6) / 19);
                Settings.equalizerModel.setReverbPreset(Settings.reverbPreset);
                try {
                    presetReverb.setPreset(Settings.reverbPreset);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                y = progress;
            }
        });

        mLinearLayout = view.findViewById(R.id.equalizerContainer);
        mLinearSoundEffect = view.findViewById(R.id.selectSound);

        mLinearSoundEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PresetAct.Companion.getIntent(requireContext()));
            }
        });

        TextView equalizerHeading = new TextView(getContext());
//        equalizerHeading.setText(R.string.eq);
        equalizerHeading.setTextSize(20);
        equalizerHeading.setGravity(Gravity.CENTER_HORIZONTAL);

        numberOfFrequencyBands = 5;

        points = new float[numberOfFrequencyBands];

        final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];
        final short upperEqualizerBandLevel = mEqualizer.getBandLevelRange()[1];

        for (short i = 0; i < numberOfFrequencyBands; i++) {
            final short equalizerBandIndex = i;
            final TextView frequencyHeaderTextView = new TextView(getContext());
            frequencyHeaderTextView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            frequencyHeaderTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            frequencyHeaderTextView.setTextColor(Color.parseColor("#FFFFFF"));
            frequencyHeaderTextView.setText((mEqualizer.getCenterFreq(equalizerBandIndex) / 1000) + "Hz");

            LinearLayout seekBarRowLayout = new LinearLayout(getContext());
            seekBarRowLayout.setOrientation(LinearLayout.VERTICAL);

            TextView lowerEqualizerBandLevelTextView = new TextView(getContext());
            lowerEqualizerBandLevelTextView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            lowerEqualizerBandLevelTextView.setTextColor(Color.parseColor("#FFFFFF"));
            lowerEqualizerBandLevelTextView.setText((lowerEqualizerBandLevel / 100) + "dB");

            TextView upperEqualizerBandLevelTextView = new TextView(getContext());
            lowerEqualizerBandLevelTextView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            upperEqualizerBandLevelTextView.setTextColor(Color.parseColor("#FFFFFF"));
            upperEqualizerBandLevelTextView.setText((upperEqualizerBandLevel / 100) + "dB");

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.weight = 1;

            SeekBar seekBar = new SeekBar(getContext());
            TextView textView = new TextView(getContext());
            switch (i) {
                case 0:
                    seekBar = view.findViewById(R.id.seekBar1);
                    textView = view.findViewById(R.id.textView1);
                    break;
                case 1:
                    seekBar = view.findViewById(R.id.seekBar2);
                    textView = view.findViewById(R.id.textView2);
                    break;
                case 2:
                    seekBar = view.findViewById(R.id.seekBar3);
                    textView = view.findViewById(R.id.textView3);
                    break;
                case 3:
                    seekBar = view.findViewById(R.id.seekBar4);
                    textView = view.findViewById(R.id.textView4);
                    break;
                case 4:
                    seekBar = view.findViewById(R.id.seekBar5);
                    textView = view.findViewById(R.id.textView5);
                    break;
            }
            seekBarFinal[i] = seekBar;
            Drawable icThumb = ContextCompat.getDrawable(requireContext(), R.drawable.ic_thumd_seekbar_v2);
            Drawable[] layers = new Drawable[2];
            layers[0] = seekBar.getThumb(); // Nút mặc định của SeekBar
            layers[1] = icThumb; // Biểu tượng từ tệp drawable
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            seekBar.setThumb(layerDrawable);

//            seekBar.getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.DKGRAY, PorterDuff.Mode.SRC_IN));
//            seekBar.getThumb().setColorFilter(new PorterDuffColorFilter(themeColor, PorterDuff.Mode.SRC_IN));

            seekBar.setId(i);
//            seekBar.setLayoutParams(layoutParams);
            seekBar.setMax(upperEqualizerBandLevel - lowerEqualizerBandLevel);

            textView.setText(frequencyHeaderTextView.getText());
            textView.setTextColor(Color.WHITE);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            if (Settings.isEqualizerReloaded) {
                points[i] = Settings.seekbarpos[i] - lowerEqualizerBandLevel;
                dataset.addPoint(frequencyHeaderTextView.getText().toString(), points[i]);
                seekBar.setProgress(Settings.seekbarpos[i] - lowerEqualizerBandLevel);
            } else {
                points[i] = mEqualizer.getBandLevel(equalizerBandIndex) - lowerEqualizerBandLevel;
                dataset.addPoint(frequencyHeaderTextView.getText().toString(), points[i]);
                seekBar.setProgress(mEqualizer.getBandLevel(equalizerBandIndex) - lowerEqualizerBandLevel);
                Settings.seekbarpos[i] = mEqualizer.getBandLevel(equalizerBandIndex);
                Settings.isEqualizerReloaded = true;
            }

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mEqualizer.setBandLevel(equalizerBandIndex, (short) (progress + lowerEqualizerBandLevel));
                    points[seekBar.getId()] = mEqualizer.getBandLevel(equalizerBandIndex) - lowerEqualizerBandLevel;
                    Settings.seekbarpos[seekBar.getId()] = (progress + lowerEqualizerBandLevel);
                    Settings.equalizerModel.getSeekbarpos()[seekBar.getId()] = (progress + lowerEqualizerBandLevel);
                    dataset.updateValues(points);
//                    chart.notifyDataUpdate();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
//                    presetSpinner.setSelection(0);
                    Settings.presetPos = 0;
                    Settings.equalizerModel.setPresetPos(0);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

//        equalizeSound();
        initAdapter();

        paint.setColor(Color.parseColor("#555555"));
        paint.setStrokeWidth((float) (1.10 * Settings.ratio));

        dataset.setColor(themeColor);
        dataset.setSmooth(true);
        dataset.setThickness(5);

//        chart.setXAxis(false);
//        chart.setYAxis(false);

//        chart.setYLabels(AxisController.LabelPosition.NONE);
//        chart.setXLabels(AxisController.LabelPosition.NONE);
//        chart.setGrid(ChartView.GridType.NONE, 7, 10, paint);

//        chart.setAxisBorderValues(-300, 3300);

//        chart.addData(dataset);
//        chart.show();

        Button mEndButton = new Button(getContext());
        mEndButton.setBackgroundColor(themeColor);
        mEndButton.setTextColor(Color.WHITE);
    }

    private EqualizerListener listener = new EqualizerListener() {
        @Override
        public void onClickEffect(int position, @NonNull Effect item) {
            try {
                if (position != 0) {
                    mEqualizer.usePreset((short) (position - 1));
                    Settings.presetPos = position;
                    short numberOfFreqBands = 5;

                    final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];

                    for (short i = 0; i < numberOfFreqBands; i++) {
                        seekBarFinal[i].setProgress(mEqualizer.getBandLevel(i) - lowerEqualizerBandLevel);
                        points[i] = mEqualizer.getBandLevel(i) - lowerEqualizerBandLevel;
                        Settings.seekbarpos[i] = mEqualizer.getBandLevel(i);
                        Settings.equalizerModel.getSeekbarpos()[i] = mEqualizer.getBandLevel(i);
                    }
                    dataset.updateValues(points);
//                        chart.notifyDataUpdate();
                }
            } catch (Exception e) {
//                Toast.makeText(ctx, "Error while updating Equalizer", Toast.LENGTH_SHORT).show();
            }
            Settings.equalizerModel.setPresetPos(position);
        }
    };

    private void initAdapter() {
//        effectRecycler.setAdapter(new EqualizerAdapter(
//                EqualizerAdapter.Companion.getDummyData(),
//                requireContext(),
//                getPosition(),
//                listener
//        ));
    }

    private Integer getPosition() {
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("myPref", Context.MODE_PRIVATE);
        if (pref != null) {
            return pref.getInt("positionEffect", 0);
        } else {
            return null;
        }
    }

//    public void equalizeSound() {
//        ArrayList<String> equalizerPresetNames = new ArrayList<>();
//        ArrayAdapter<String> equalizerPresetSpinnerAdapter = new ArrayAdapter<>(ctx,
//                R.layout.spinner_item,
//                equalizerPresetNames);
//        equalizerPresetSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        equalizerPresetNames.add("Custom");
//
//        for (short i = 0; i < mEqualizer.getNumberOfPresets(); i++) {
//            equalizerPresetNames.add(mEqualizer.getPresetName(i));
//        }
//
//        presetSpinner.setAdapter(equalizerPresetSpinnerAdapter);
//        //presetSpinner.setDropDownWidth((Settings.screen_width * 3) / 4);
//        if (Settings.isEqualizerReloaded && Settings.presetPos != 0) {
////            correctPosition = false;
//            presetSpinner.setSelection(Settings.presetPos);
//        }
//
//        presetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    if (position != 0) {
//                        mEqualizer.usePreset((short) (position - 1));
//                        Settings.presetPos = position;
//                        short numberOfFreqBands = 5;
//
//                        final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];
//
//                        for (short i = 0; i < numberOfFreqBands; i++) {
//                            seekBarFinal[i].setProgress(mEqualizer.getBandLevel(i) - lowerEqualizerBandLevel);
//                            points[i] = mEqualizer.getBandLevel(i) - lowerEqualizerBandLevel;
//                            Settings.seekbarpos[i] = mEqualizer.getBandLevel(i);
//                            Settings.equalizerModel.getSeekbarpos()[i] = mEqualizer.getBandLevel(i);
//                        }
//                        dataset.updateValues(points);
////                        chart.notifyDataUpdate();
//                    }
//                } catch (Exception e) {
//                    Toast.makeText(ctx, "Error while updating Equalizer", Toast.LENGTH_SHORT).show();
//                }
//                Settings.equalizerModel.setPresetPos(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }


    @Override
    public void onResume() {
        super.onResume();
        int position = sharePrefs.getAddSoundPosition();
        Log.d("ThangND equalizer", "onResume: " + position);
        try {
            if (position != 0) {
                mEqualizer.usePreset((short) (position - 1));
                Settings.presetPos = position;
                short numberOfFreqBands = 5;

                final short lowerEqualizerBandLevel = mEqualizer.getBandLevelRange()[0];

                for (short i = 0; i < numberOfFreqBands; i++) {
                    seekBarFinal[i].setProgress(mEqualizer.getBandLevel(i) - lowerEqualizerBandLevel);
                    points[i] = mEqualizer.getBandLevel(i) - lowerEqualizerBandLevel;
                    Settings.seekbarpos[i] = mEqualizer.getBandLevel(i);
                    Settings.equalizerModel.getSeekbarpos()[i] = mEqualizer.getBandLevel(i);
                }
                dataset.updateValues(points);
//                        chart.notifyDataUpdate();
            }
        } catch (Exception e) {
//            Toast.makeText(ctx, "Error while updating Equalizer", Toast.LENGTH_SHORT).show();
        }
        Settings.equalizerModel.setPresetPos(position);

        String name = sharePrefs.getAddSoundName();
        nameEffect.setText(name);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mEqualizer != null) {
            mEqualizer.release();
        }

        if (bassBoost != null) {
            bassBoost.release();
        }

        if (presetReverb != null) {
            presetReverb.release();
        }

        Settings.isEditing = false;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private int id = -1;

        public Builder setAudioSessionId(int id) {
            this.id = id;
            return this;
        }

        public Builder setAccentColor(int color) {
            themeColor = color;
            return this;
        }

        public Builder setShowBackButton(boolean show) {
            showBackButton = show;
            return this;
        }

        public EqualizerFrV2 build() {
            return EqualizerFrV2.newInstance(id);
        }
    }
}