package agp32.dev;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class ContextMenuFragment<Source> extends DialogFragment {

    /**
     * Source of this context menu
     * (e.g. list item that was clicked).
     */
    @Getter private Source source;

    /**
     * List of options you want to show.
     */
    @Getter private final List<ContextMenu.Option> options = new ArrayList<ContextMenu.Option>();

    /**
     * @param source {@link #source}
     */
    public ContextMenuFragment(@NonNull Source source) {
        this.source = source;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ContextMenu<Source> contextMenu = new ContextMenu<Source>(getActivity(), source);
        contextMenu.getOptions().addAll(options);
        return contextMenu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        getDialog().setCanceledOnTouchOutside(true);
        return super.onCreateView(inflater, container, state);
    }
}
