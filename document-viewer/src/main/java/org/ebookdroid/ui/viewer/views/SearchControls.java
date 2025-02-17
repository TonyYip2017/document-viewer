package org.ebookdroid.ui.viewer.views;

import org.sufficientlysecure.viewer.R;
import org.ebookdroid.ui.viewer.ViewerActivity;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import org.emdev.ui.actions.ActionEx;
import org.emdev.ui.actions.params.Constant;
import org.emdev.ui.actions.params.EditableValue;

public class SearchControls extends LinearLayout {

    private final EditText m_edit;

    public SearchControls(final ViewerActivity parent) {
        super(parent);
        setVisibility(View.GONE);
        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater.from(parent).inflate(R.layout.seach_controls, this, true);
        ImageButton m_prevButton = findViewById(R.id.search_controls_prev);
        ImageButton m_nextButton = findViewById(R.id.search_controls_next);
        m_edit = findViewById(R.id.search_controls_edit);

        ActionEx forwardSearch = parent.getController().getOrCreateAction(R.id.actions_doSearch);
        ActionEx backwardSearch = parent.getController().getOrCreateAction(R.id.actions_doSearchBack);

        forwardSearch.addParameter(new EditableValue("input", m_edit)).addParameter(new Constant("forward", "true"));
        backwardSearch.addParameter(new EditableValue("input", m_edit)).addParameter(new Constant("forward", "false"));

        m_prevButton.setOnClickListener(backwardSearch);
        m_nextButton.setOnClickListener(forwardSearch);
        m_edit.setOnEditorActionListener(forwardSearch);
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            m_edit.requestFocus();
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return false;
    }

    public int getActualHeight() {
        return m_edit.getHeight();
    }
}
