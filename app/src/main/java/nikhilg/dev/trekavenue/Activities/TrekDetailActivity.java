package nikhilg.dev.trekavenue.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import nikhilg.dev.trekavenue.Data.ImageDto;
import nikhilg.dev.trekavenue.Data.TrekDataDto;
import nikhilg.dev.trekavenue.R;
import nikhilg.dev.trekavenue.Utils.Constants;

public class TrekDetailActivity extends AppCompatActivity {

    // layout items
    private FrameLayout topImageContainer;
    private ImageView imageView;
    private TextView trekName;

    private int width, height;

    private TrekDataDto trekObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trek_detail);

        width = getWindowManager().getDefaultDisplay().getWidth();
        height = width*2/3;

        trekObject = new Gson().fromJson(getIntent().getStringExtra(Constants.TREK_OBJECT), TrekDataDto.class);

        initLayout();
    }

    private void initLayout() {
        topImageContainer = (FrameLayout) findViewById(R.id.topImageContainer);
        topImageContainer.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        imageView = (ImageView) findViewById(R.id.imageView);

        for (ImageDto temp : trekObject.getImages()) {
            if (temp.getIndex() == 0) {
                Picasso.with(this)
                        .load(temp.getUrl())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(imageView);
                break;
            }
        }

        trekName = (TextView) findViewById(R.id.trekName);
        trekName.setText(trekObject.getName());
    }
}
