package com.abdulrafay.barcoder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.os.Bundle;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class MainActivity extends AppCompatActivity {

    ImageView codeViewer;
    Button GenBtn;
    Button PrinterBtn;
    EditText InputTxt;
    Bitmap BarcodeBitMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        codeViewer =  findViewById(R.id.barcodeView);
        GenBtn = (Button) findViewById(R.id.GenerateButton);
        PrinterBtn = (Button) findViewById(R.id.PrintBtn) ;
        InputTxt = (EditText) findViewById(R.id.CodeTxt);
        GenBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("a",InputTxt.getText().toString());
                BitMatrix bit = GenerateBarcode(InputTxt.getText().toString());
                if(bit != null) {
                    BarcodeBitMap = Bitmap.createBitmap(codeViewer.getWidth(), codeViewer.getHeight(), Bitmap.Config.RGB_565);
                    for (int i = 0; i < codeViewer.getWidth(); i++) {
                        for (int j = 0; j < codeViewer.getHeight(); j++) {
                            BarcodeBitMap.setPixel(i, j, bit.get(i, j) ? Color.BLACK : Color.WHITE);
                        }
                    }
                    codeViewer.setImageBitmap(BarcodeBitMap);
                }
            }
        });

        PrinterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrintImage();
            }
        });


    }

    public BitMatrix GenerateBarcode(String contents)
    {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix bit = writer.encode(contents,BarcodeFormat.CODE_128,codeViewer.getWidth(),codeViewer.getHeight());
            return bit;
        }
        catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void PrintImage()
    {
        PrintHelper photoPrinter = new PrintHelper(this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("barcode-print test", BarcodeBitMap);
    }
}