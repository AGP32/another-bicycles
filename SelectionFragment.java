package agp32.dev;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import agp32.dev.prop.Property;
import agp32.dev.prop.PropertyBase;
import agp32.rec.R;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

public class SelectionFragment<Selectable> extends Fragment {

    /**
     * ResultSource will be started with this code.
     */
    public static final int SELECTION_RESULT_CODE = 1;

    //
    // Layout
    //

    private LinearLayout layout;
    private LayoutMap layoutMap;

    /**
     * Layout "map" for setting id's of view components.
     */
    @Accessors(chain = true)
    public static class LayoutMap {

        /**
         * Layout to use for this fragment.
         */
        @Getter @Setter
        private int layout = R.layout.agp32_selection_fragment;

        /**
         * Id of TextView located in {@link #layout}.
         */
        @Getter @Setter
        private int textView = R.id.agp32_selection_fragment_text;

        /**
         * Id of ImageButton located in {@link #layout}.
         */
        @Getter @Setter
        private int imageButton = R.id.agp32_selection_fragment_button;
    }


    public SelectionFragment() {
        this(new LayoutMap());
    }

    public SelectionFragment(LayoutMap layoutMap) {
        this.layoutMap = layoutMap;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle bundle) {
        layout = (LinearLayout) inflater.inflate(layoutMap.layout, group, false);
        updateText();
        updateImageButton();
        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Selectable selectable = null;
        if(resultCode == Activity.RESULT_OK) {
            selectable = getResultConverter().call(data);
        }
        selectedObjectProperty.setValue(selectable);
    }

    //
    // EditText
    //

    private EditText editText;
    private EditText editText() {
        if(editText == null && layout != null) {
            editText = (EditText) layout.findViewById(layoutMap.textView);
        }
        return editText;
    }

    private void updateText() {
        EditText et = editText();
        if(et == null) return;

        Selectable selectedObject = selectedObjectProperty.getValue();
        if(selectedObject == null) {
            String hint = hintProperty.getValue();
            et.setText("");
            if(hint != null) {
                et.setHint(hint);
            }
        } else {
            String textRepresentation = getValueAdapter().call(selectedObject);
            et.setText(textRepresentation);
        }
    }

    //
    // ImageButton
    //

    private ImageButton imageButton;
    private ImageButton imageButton() {
        if(imageButton == null && layout != null) {
            imageButton = (ImageButton) layout.findViewById(layoutMap.imageButton);
            imageButton.requestFocus();
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(intentFactory != null) {
                        Intent intent = intentFactory.call();
                        startActivityForResult(intent, SELECTION_RESULT_CODE);
                    }
                }
            });
        }
        return imageButton;
    }

    private void updateImageButton() {
        ImageButton ib = imageButton();
        if(ib == null) return;

        Drawable icon = iconProperty.getValue();
        if(icon != null) {
            ib.setImageDrawable(icon);
        }
    }

    //
    // Intent factory
    //

    public interface IntentFactory {
        public Intent call();
    }

    @Getter @Setter @NonNull
    private IntentFactory intentFactory;

    //
    // Result Converter
    //

    public interface ResultConverter<Selectable> {
        public Selectable call(Intent data);
    }

    @Setter @Getter @NonNull
    private ResultConverter<Selectable> resultConverter;

    //
    // Value factory
    //

    public interface ValueAdapter<Selectable> {
        public String call(Selectable selectable);
    }

    @Setter @Getter @NonNull
    private ValueAdapter<Selectable> valueAdapter;

    //
    // Properties
    //

    public final Property<Selectable> selectedObjectProperty = new Property<Selectable>();
    {
        selectedObjectProperty.addListener(new PropertyBase.ChangeListener<Selectable>() {
            @Override
            public void call(PropertyBase<Selectable> source,  Selectable ov, Selectable nv) {
                updateText();
            }
        });
    }

    public final Property<String> hintProperty = new Property<String>();
    {
        hintProperty.addListener(new PropertyBase.ChangeListener<String>() {

            @Override
            public void call(PropertyBase<String> source, String oldValue, String newValue) {
                updateText();
            }
        });
    }

    public final Property<Drawable> iconProperty = new Property<Drawable>();
    {
        iconProperty.addListener(new PropertyBase.ChangeListener<Drawable>() {
            @Override
            public void call(PropertyBase<Drawable> source, Drawable oldValue, Drawable newValue) {
                updateImageButton();
            }
        });
    }
}
