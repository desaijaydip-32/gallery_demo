package com.example.gallerydemo.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.Log;

import android.view.View;

import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.gallerydemo.Adapter.PhotoFilter;
import com.example.gallerydemo.FilterListener;
import com.example.gallerydemo.R;


import com.mukesh.image_processing.ImageProcessor;

import java.io.File;
import java.io.IOException;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.TextStyleBuilder;
import ja.burhanrashid52.photoeditor.shape.ShapeType;


public class EditImageActivity1 extends AppCompatActivity implements OnPhotoEditorListener,View.OnClickListener,
        PropertiesBSFragment.Properties,
        ShapeBSFragment.Properties,
        EmojiBSFragment.EmojiListener,
        StickerBSFragment.StickerListener, EditingToolsAdapter.OnItemSelected, FilterListener {


    private static final String TAG = EditImageActivity1.class.getSimpleName();
    public static final String FILE_PROVIDER_AUTHORITY = "com.burhanrashid52.photoeditor.fileprovider";
    private static final int CAMERA_REQUEST = 52;
    private static final int PICK_REQUEST = 53;
    public static final String ACTION_NEXTGEN_EDIT = "action_nextgen_edit";
    public static final String PINCH_TEXT_SCALABLE_INTENT_KEY = "PINCH_TEXT_SCALABLE";



    PhotoEditor mPhotoEditor;
    private PhotoEditorView mPhotoEditorView;
   PropertiesBSFragment mPropertiesBSFragment;
    private ShapeBSFragment mShapeBSFragment;
    private ShapeBuilder mShapeBuilder;
    private EmojiBSFragment mEmojiBSFragment;
    private StickerBSFragment mStickerBSFragment;
     TextView mTxtCurrentTool;
     Typeface mWonderFont;
    private RecyclerView mRvTools, mRvFilters;
    private final EditingToolsAdapter mEditingToolsAdapter = new EditingToolsAdapter(this);
    private final FilterViewAdapter mFilterViewAdapter = new FilterViewAdapter(this);
    private ConstraintLayout mRootView;
    private final ConstraintSet mConstraintSet = new ConstraintSet();
    private boolean mIsFilterVisible;





    @Nullable
    @VisibleForTesting
    Uri mSaveImageUri;

    private FileSaveHelper mSaveFileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen_size);

        initViews();

        handleIntentImage(mPhotoEditorView.getSource());

        mWonderFont = Typeface.createFromAsset(getAssets(), "beyond_wonderland.ttf");

        mPropertiesBSFragment = new PropertiesBSFragment();
        mEmojiBSFragment = new EmojiBSFragment();
        mStickerBSFragment = new StickerBSFragment();
        mShapeBSFragment = new ShapeBSFragment();
        mStickerBSFragment.setStickerListener(this);
        mEmojiBSFragment.setEmojiListener(this);
        mPropertiesBSFragment.setPropertiesChangeListener(this);
        mShapeBSFragment.setPropertiesChangeListener(this);

        LinearLayoutManager llmTools = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvTools.setLayoutManager(llmTools);
        mRvTools.setAdapter(mEditingToolsAdapter);

        LinearLayoutManager llmFilters = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvFilters.setLayoutManager(llmFilters);
        mRvFilters.setAdapter(mFilterViewAdapter);

        // NOTE(lucianocheng): Used to set integration testing parameters to PhotoEditor
        boolean pinchTextScalable = getIntent().getBooleanExtra(PINCH_TEXT_SCALABLE_INTENT_KEY, true);

        //Typeface mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium);
        //Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");

        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(pinchTextScalable) // set flag to make text scalable when pinch
                //.setDefaultTextTypeface(mTextRobotoTf)
                //.setDefaultEmojiTypeface(mEmojiTypeFace)
                .build(); // build photo editor sdk

        mPhotoEditor.setOnPhotoEditorListener(this);

        //Set Image Dynamically
        mPhotoEditorView.getSource().setImageResource(R.drawable.paris_tower);

        mSaveFileHelper = new FileSaveHelper(this);
    }

    private void handleIntentImage(ImageView source) {
        Intent intent = getIntent();
        if (intent != null) {
            // NOTE(lucianocheng): Using "yoda conditions" here to guard against
            //                     a null Action in the Intent.
            if (Intent.ACTION_EDIT.equals(intent.getAction()) ||
                    ACTION_NEXTGEN_EDIT.equals(intent.getAction())) {
                try {
                    Uri uri = intent.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    source.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String intentType = intent.getType();
                if (intentType != null && intentType.startsWith("image/")) {
                    Uri imageUri = intent.getData();
                    if (imageUri != null) {
                        source.setImageURI(imageUri);
                    }
                }
            }
        }
    }

    private void initViews() {
        ImageView imgUndo;
        ImageView imgRedo;
        ImageView imgCamera;
        ImageView imgGallery;
        ImageView imgSave;
        ImageView imgClose;
        ImageView imgShare;


        mTxtCurrentTool = findViewById(R.id.txtCurrentTool);
        mRvTools = findViewById(R.id.rvConstraintTools);
        mRvFilters = findViewById(R.id.rvFilterView);
        mRootView = findViewById(R.id.rootView);

        imgUndo = findViewById(R.id.imgUndo);
        imgUndo.setOnClickListener(this);

        imgRedo = findViewById(R.id.imgRedo);
        imgRedo.setOnClickListener(this);

        imgCamera = findViewById(R.id.imgCamera);
        imgCamera.setOnClickListener(this);

        imgGallery = findViewById(R.id.imgGallery);
        imgGallery.setOnClickListener(this);

        imgSave = findViewById(R.id.imgSave);
        imgSave.setOnClickListener(this);

        imgClose = findViewById(R.id.imgClose);
        imgClose.setOnClickListener(this);

        imgShare = findViewById(R.id.imgShare);
        imgShare.setOnClickListener(this);

    }

    @Override
    public void onEditTextChangeListener(final View rootView, String text, int colorCode) {
        TextEditorDialogFragment textEditorDialogFragment =
                TextEditorDialogFragment.show(this, text, colorCode);
        textEditorDialogFragment.setOnTextEditorListener((inputText, newColorCode) -> {
            final TextStyleBuilder styleBuilder = new TextStyleBuilder();
            styleBuilder.withTextColor(newColorCode);

            mPhotoEditor.editText(rootView, inputText, styleBuilder);
            mTxtCurrentTool.setText(R.string.label_text);
        });
    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {
        Log.d(TAG, "onRemoveViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {
        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
    }

    @Override
    public void onTouchSourceImage(MotionEvent event) {
        Log.d(TAG, "onTouchView() called with: event = [" + event + "]");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgUndo:
                mPhotoEditor.undo();
                break;

            case R.id.imgRedo:
                mPhotoEditor.redo();
                break;

            case R.id.imgSave:
                saveImage();
                break;

            case R.id.imgClose:
                onBackPressed();
                break;
            case R.id.imgShare:
                shareImage();
                break;

            case R.id.imgCamera:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;

            case R.id.imgGallery:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
                break;
        }
    }

    private void shareImage() {
        if (mSaveImageUri == null) {
            showSnackbar(getString(R.string.msg_save_image_to_share));
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, buildFileProviderUri(mSaveImageUri));
        startActivity(Intent.createChooser(intent, getString(R.string.msg_share_image)));
    }

    private Uri buildFileProviderUri(@NonNull Uri uri) {
        return FileProvider.getUriForFile(this,
                FILE_PROVIDER_AUTHORITY,
                new File(uri.getPath()));
    }


    private void saveImage() {
        final String fileName = System.currentTimeMillis() + ".png";
        final boolean hasStoragePermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED;
        if (hasStoragePermission || isSdkHigherThan28()) {
            showLoading("Saving...");
            mSaveFileHelper.createFile(fileName, (fileCreated, filePath, error, uri) -> {
                if (fileCreated) {
                    SaveSettings saveSettings = new SaveSettings.Builder()
                            .setClearViewsEnabled(true)
                            .setTransparencyEnabled(true)
                            .build();

                    mPhotoEditor.saveAsFile(filePath, saveSettings, new PhotoEditor.OnSaveListener() {
                        @Override
                        public void onSuccess(@NonNull String imagePath) {
                            mSaveFileHelper.notifyThatFileIsNowPubliclyAvailable(getContentResolver());
                            hideLoading();
                            showSnackbar("Image Saved Successfully");
                            mSaveImageUri = uri;
                            mPhotoEditorView.getSource().setImageURI(mSaveImageUri);
                        }

                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            hideLoading();
                            showSnackbar("Failed to save Image");
                        }
                    });

                } else {
                    hideLoading();
                    showSnackbar(error);
                }
            });
        } else {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST:
                    mPhotoEditor.clearAllViews();
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    mPhotoEditorView.getSource().setImageBitmap(photo);
                    break;
                case PICK_REQUEST:
                    try {
                        mPhotoEditor.clearAllViews();
                        Uri uri = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        mPhotoEditorView.getSource().setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public void onColorChanged(int colorCode) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeColor(colorCode));
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onOpacityChanged(int opacity) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeOpacity(opacity));
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onShapeSizeChanged(int shapeSize) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeSize(shapeSize));
        mTxtCurrentTool.setText(R.string.label_brush);
    }

    @Override
    public void onShapePicked(ShapeType shapeType) {
        mPhotoEditor.setShape(mShapeBuilder.withShapeType(shapeType));
    }

    @Override
    public void onEmojiClick(String emojiUnicode) {
        mPhotoEditor.addEmoji(emojiUnicode);
        mTxtCurrentTool.setText(R.string.label_emoji);
    }

    @Override
    public void onStickerClick(Bitmap bitmap) {
        mPhotoEditor.addImage(bitmap);
        mTxtCurrentTool.setText(R.string.label_sticker);
    }

    @Override
    public void isPermissionGranted(boolean isGranted, String permission) {
        if (isGranted) {
            saveImage();
        }
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.msg_save_image));
        builder.setPositiveButton("Save", (dialog, which) -> saveImage());
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.setNeutralButton("Discard", (dialog, which) -> finish());
        builder.create().show();

    }

    @Override
    public void onFilterSelected(PhotoFilter photoFilter) {
        mPhotoEditor.setFilterEffect(photoFilter);
    }

    @Override
    public void onToolSelected(ToolType toolType) {
        switch (toolType) {
            case SHAPE:
                mPhotoEditor.setBrushDrawingMode(true);
                mShapeBuilder = new ShapeBuilder();
                mPhotoEditor.setShape(mShapeBuilder);
                mTxtCurrentTool.setText(R.string.label_shape);
                showBottomSheetDialogFragment(mShapeBSFragment);
                break;
            case TEXT:
                TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
                textEditorDialogFragment.setOnTextEditorListener((inputText, colorCode) -> {
                    final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                    styleBuilder.withTextColor(colorCode);

                    mPhotoEditor.addText(inputText, styleBuilder);
                    mTxtCurrentTool.setText(R.string.label_text);
                });
                break;
            case ERASER:
                mPhotoEditor.brushEraser();
                mTxtCurrentTool.setText(R.string.label_eraser_mode);
                break;
            case FILTER:
                mTxtCurrentTool.setText(R.string.label_filter);
                showFilter(true);
                break;
            case EMOJI:
                showBottomSheetDialogFragment(mEmojiBSFragment);
                break;
            case STICKER:
                showBottomSheetDialogFragment(mStickerBSFragment);
                break;
        }
    }

    private void showBottomSheetDialogFragment(BottomSheetDialogFragment fragment) {
        if (fragment == null || fragment.isAdded()) {
            return;
        }
        fragment.show(getSupportFragmentManager(), fragment.getTag());
    }


    void showFilter(boolean isVisible) {
        mIsFilterVisible = isVisible;
        mConstraintSet.clone(mRootView);

        if (isVisible) {
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.START);
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.END,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
        } else {
            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
                    ConstraintSet.PARENT_ID, ConstraintSet.END);
            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.END);
        }

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(350);
        changeBounds.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        TransitionManager.beginDelayedTransition(mRootView, changeBounds);

        mConstraintSet.applyTo(mRootView);
    }

    @Override
    public void onBackPressed() {
        if (mIsFilterVisible) {
            showFilter(false);
            mTxtCurrentTool.setText(R.string.app_name);
        } else if (!mPhotoEditor.isCacheEmpty()) {
            showSaveDialog();
        } else {
            super.onBackPressed();
        }
    }



//    ActivityFullScreenSizeBinding binding;
//    private String path1;
//    public int img_icon[];
//    private  String  picName[];
//    public Bitmap[] filter_img;
//    public String filtet_menu[];
//    private ImageView imageView;
//    boolean clicked = true;
//    TextView cancelbtn, deletbtn;
//    Bitmap oneBitMap, twoBitMap, threeBitmap, fourBitMap, fiveBitMap, sixBitMap, sevenBitMap, eightBitMap, nineBitMap, tenBitMap;
//    private float xPos;
//    private float yPos;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityFullScreenSizeBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        path1 = getIntent().getStringExtra("imgpath");
//        Glide.with(this).load(path1).into(binding.recyclerview);
//
//        binding.imageView4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (clicked) {
//                    binding.imageView4.setImageResource(R.drawable.ic_ike);
//                    try {
//
//                        MediaStore.Images.Media.insertImage(getContentResolver(), path1, "img2", "data1");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    startActivity(new Intent(FullScreenSizeActivity.this, MainActivity.class));
//                } else {
//                    binding.imageView4.setImageResource(R.drawable.ic_dislike);
//                }
//            }
//        });
//
//        binding.bottomNavigation.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @SuppressLint("NonConstantResourceId")
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                        switch (item.getItemId()) {
//                            case R.id.share:
//                                shareImages(path1);
//                                break;
//
//                            case R.id.edit:
//                                effectImg(path1);
//                                break;
//
//                            case R.id.editTextImage:
//                                editTextImage(path1);
//                                break;
//                            case R.id.delete:
//                                deletimage(path1);
//                                break;
//
//                            case R.id.details:
//                                detailsImages(path1);
//                                break;
//                            default:
//
//                        }
//                        return true;
//                    }
//                });
//    }
//
//    private void editTextImage(String path1) {
//
//        binding.bottomNavigation.setVisibility(View.GONE);
//        binding.editTextText.setVisibility(View.VISIBLE);
//        binding.linear.setVisibility(View.VISIBLE);
//
//        binding.cancelImageView6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.bottomNavigation.setVisibility(View.VISIBLE);
//                binding.linear.setVisibility(View.GONE);
//                binding.editTextText.setVisibility(View.GONE);
//            }
//        });
//
//
//
//        binding.saveImageView6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                setTExtView(path1, binding.editTextText.getText().toString());
//            }
//        });
//
//
//        // View view = LayoutInflater.from(FullScreenSizeActivity.this).inflate(R.layout.custom_layoyt,null, false);
//        // binding.linear.addView(view);
//
//    }
//
//    private void setTExtView(String path1, String text) {
//
//
//
//
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), Integer.parseInt(path1)).copy(Bitmap.Config.ARGB_8888, true);
//        Typeface tf = Typeface.create("Helvetica", Typeface.BOLD);
//        Paint paint = new Paint();
//
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.WHITE);
//        paint.setTypeface(tf);
//        paint.setTextAlign(Paint.Align.CENTER);
//        paint.setTextSize(11);
//        Rect textRect = new Rect();
//        paint.getTextBounds(text, 0, text.length(), textRect);
//
//
//        Canvas canvas = new Canvas(bm);
//        canvas.drawText(text, xPos, yPos, paint);
//
//       // Toast.makeText(this, ""+canvas, Toast.LENGTH_SHORT).show();
//    }
//
//
////    private void getStorageDir(String path)
////    {
////
////        File file = new File(Environment.getExternalStorageDirectory() + "/folderName/folderName1");
////        if (!file.mkdirs()) {
////            file.mkdirs();
////        }
////
////        String filePath = file.getAbsolutePath() + File.separator + file;
////        ContentValues values = new ContentValues();
////        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
////        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
////        values.put(MediaStore.MediaColumns.DATA, path);
////        getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
////
////    }
//
//    private void effectImg(String path) {
//
//        // binding.recyclerview12.setVisibility(View.VISIBLE);
//
//
//
//        binding.bottomNavigation.setVisibility(View.GONE);
//        binding.recyclerview1.setVisibility(View.VISIBLE);
//        binding.saveTextView.setVisibility(View.VISIBLE);
//        binding.cancleImg.setVisibility(View.VISIBLE);
//
//
//        img_icon = new int[]{R.drawable.ic_filter};
//        filtet_menu = new String[]{"filter"};
//        imageView = findViewById(R.id.recyclerview);
//
//
////        binding.recyclerview12.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
////        binding.recyclerview12.setAdapter(new FilterMenuAdapter(FullScreenSizeActivity.this, img_icon, filtet_menu, new FilterMenuAdapter.PostitonPass() {
////            @Override
////            public void sendPostiton(int pos) {
////                if (pos == 1) {
////
////                    binding.editTextText.setVisibility(View.VISIBLE);
////
////                    writeTextOnDrawable(path, binding.editTextText.getText().toString());
////                } else {
////                    binding.editTextText.setVisibility(View.GONE);
////                }
////            }
////        }));
//
//
//        File imgFile = new File(path);
//        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//        ImageProcessor processor = new ImageProcessor();
//
//
//        oneBitMap = processor.tintImage(myBitmap, 90);
//        twoBitMap = processor.applySnowEffect(myBitmap);
//        fourBitMap = processor.createSepiaToningEffect(myBitmap, 1, 2, 1, 5);
//
//
//        filter_img = new Bitmap[]{ myBitmap, oneBitMap, twoBitMap, fourBitMap};
//
//        picName = new String[]{"orignal", "titn","snow", "sepia"};
//
//        binding.recyclerview1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        binding.recyclerview1.setAdapter(new EffectAdapter(FullScreenSizeActivity.this, filter_img, imageView, picName,new EffectAdapter.SaveImageBitmap() {
//            @Override
//            public void saveImage(ImageView bitmap) {
//
//                binding.saveTextView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        bitmap.buildDrawingCache();
//                        Bitmap bmap = imageView.getDrawingCache();
//                        try {
//                            MediaStore.Images.Media.insertImage(getContentResolver(), bmap, "img", "data");
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        Toast.makeText(FullScreenSizeActivity.this, "Successfully Stored", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(FullScreenSizeActivity.this, MainActivity.class));
//
//                    }
//                });
//
//            }
//        }));
//
//
//        binding.cancleImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                binding.saveTextView.setVisibility(View.INVISIBLE);
//                binding.cancleImg.setVisibility(View.GONE);
//                binding.bottomNavigation.setVisibility(View.VISIBLE);
//                binding.recyclerview1.setVisibility(View.GONE);
//
//                //  bibottom_navigationnding.recyclerview12.setVisibility(View.GONE);
//
//            }
//        });
//
//    }
//
//    private void writeTextOnDrawable(String path, String text) {
//        File imgFile = new File(path);
//        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//        try {
//
//            MediaStore.Images.Media.insertImage(getContentResolver(), myBitmap, "img", "data");
//            startActivity(new Intent(FullScreenSizeActivity.this, MainActivity.class));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    private float convertToPixels(Context context, int nDP) {
//        final float conversionScale = context.getResources().getDisplayMetrics().density;
//        return (int) ((nDP * conversionScale) + 0.5f);
//    }
//
//
//    private void detailsImages(String path) {
//        TextView pathtxt, titletxt, sizetxt, datatxt, canceltxt;
//
//
//        BottomSheetDialog dialog = new BottomSheetDialog(FullScreenSizeActivity.this, R.style.bottomsheetstle);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//
//        View view = LayoutInflater.from(FullScreenSizeActivity.this).inflate(R.layout.details_layout, (ConstraintLayout) findViewById(R.id.contstrain_layout));
//
//        sizetxt = view.findViewById(R.id.textView4);
//        pathtxt = view.findViewById(R.id.textView5);
//        titletxt = view.findViewById(R.id.textView6);
////        datatxt = view.findViewById(R.id.textView8);
//        canceltxt = view.findViewById(R.id.cancletextView);
//
//
//        dialog.setContentView(view);
//        File file = new File(path);
//        String dirPath = file.getAbsolutePath();
//        String imageName = file.getName();
//
//
//        long fileSizeInBytes = file.length();
//
//        Double mSize = Double.parseDouble(fileSizeInBytes / 1024 + "");
//        sizetxt.setText(String.format("%skb", mSize));
//
////        if (fileSizeInBytes < 1024) {
////
//////            Long  fileSizeInKB =(fileSizeInBytes /1024) ;
////
////           // String dd =formatchange(fileSizeInKB);
////
////            sizetxt.setText(fileSizeInBytes+ "kb");
////
////        } else if (fileSizeInBytes > 1024) {
////
//////            Long dumy= (fileSizeInBytes / 2058);
////
//////            String ddx =formatchange(dumy);
////
////            sizetxt.setText(fileSizeInBytes+ "Mb");
////        }
//
//
//        if (file.exists()) {
//            ExifInterface intf;
//
//            try {
//
//                intf = new ExifInterface(path);
//                String dateString = intf.getAttribute(ExifInterface.TAG_DATETIME);
//                //datatxt.setText(dateString);
//
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        pathtxt.setText(dirPath);
//        titletxt.setText(imageName);
//        canceltxt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//
//
//    }
//
//
//    private void shareImages(String fileUri) {
//
//        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
//        Uri imageUri = Uri.parse(fileUri);
//        sharingIntent.setType("image/*");
//        sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
//        startActivity(sharingIntent);
//    }
//
//    private void deletimage(String path) {
//
//
//        BottomSheetDialog dialog = new BottomSheetDialog(FullScreenSizeActivity.this, R.style.bottomsheetstle);
//
//        View view = LayoutInflater.from(FullScreenSizeActivity.this).inflate(R.layout.delet_custom, (ConstraintLayout) findViewById(R.id.contstain));
//        deletbtn = view.findViewById(R.id.button5);
//        cancelbtn = view.findViewById(R.id.button4);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        deletbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                File fdelete = new File(path);
//                if (fdelete.exists()) {
//                    if (fdelete.delete()) {
//                        FullScreenSizeActivity.this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(path))));
//                        Toast.makeText(FullScreenSizeActivity.this, "Successfully Delet", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(FullScreenSizeActivity.this, MainActivity.class));
//
//                    } else {
//
//                        Toast.makeText(FullScreenSizeActivity.this, "delet not succesfully", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            }
//        });
//
//        cancelbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//
//        dialog.setContentView(view);
//        dialog.show();
//
//
//    }


}