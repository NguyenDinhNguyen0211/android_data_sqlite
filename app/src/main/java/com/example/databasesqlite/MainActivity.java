package com.example.databasesqlite;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private Button btnCreDb, btnDelDb, btnCreTbl, btnDelTbl, btnInsRowTblLop, btnDelRowTblLop, btnUpdateRowTblLop, btnQueryTblLop, btnInsSv, btnQuerySvByLop;

    private EditText txtDelTbl, txtMalop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initView() {
        btnCreDb = (Button) findViewById(R.id.btnCreDb);
        btnDelDb = (Button) findViewById(R.id.btnDelDb);
        btnCreTbl = (Button) findViewById(R.id.btnCreTbl);
        btnDelTbl = (Button) findViewById(R.id.btnDelTbl);
        btnInsRowTblLop = (Button) findViewById(R.id.btnInsRowTblLop);
        btnDelRowTblLop = (Button) findViewById(R.id.btnDelRowTblLop);
        btnUpdateRowTblLop = (Button) findViewById(R.id.btnUpdateRowTblLop);
        btnQueryTblLop = (Button) findViewById(R.id.btnQueryTblLop);
        btnInsSv = (Button) findViewById(R.id.btnInsSv);
        btnQuerySvByLop = (Button) findViewById(R.id.btnQuerySvByLop);
        txtDelTbl = (EditText) findViewById(R.id.txtDelTbl);
        txtMalop = (EditText) findViewById(R.id.txtMalop);
    }

    private void initListener() {
        // create database
        btnCreDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInputNameDb(Gravity.CENTER);
            }
        });
        // drop database
        btnDelDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getApplicationContext().deleteDatabase(dbHelper.getNameDatabase())) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // create table
        btnCreTbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInputNameTbl(Gravity.CENTER);
            }
        });
        // drop table
        btnDelTbl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.queryData("DROP TABLE IF EXISTS " + txtDelTbl.getText().toString());
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        });
        // insert data
        btnInsRowTblLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsertDataLop(Gravity.CENTER);
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        });
        // del data
        btnDelRowTblLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDelDataLop(Gravity.CENTER);
            }
        });
        // update data
        btnUpdateRowTblLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataLop(Gravity.CENTER);
            }
        });
        // query data lop
        btnQueryTblLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDataLop(Gravity.CENTER);
            }
        });
        //insert data Student
        btnInsSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ref = txtMalop.getText().toString();
                openInsertDataSv(Gravity.CENTER, ref);
            }
        });
        // query data sv by lop
        btnQuerySvByLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryDataSvByLop(Gravity.CENTER);
            }
        });
    }

    public static void ShowSuccessAlert(final Context context, String heading, String description, DialogInterface.OnClickListener action) {
        new AlertDialog.Builder(context).setTitle(heading).setMessage(description).setPositiveButton(android.R.string.yes, action).setIcon(android.R.drawable.ic_dialog_info).show();
    }

    private void wind(int gravity, Dialog dialog, int xml) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(xml);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        if (Gravity.BOTTOM == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams winAttributes = window.getAttributes();
        winAttributes.gravity = gravity;
        window.setAttributes(winAttributes);
    }

    private void openInputNameDb(int gravity) {
        final Dialog dialog = new Dialog(this);
        wind(gravity, dialog, R.layout.input_name_db);
        Button btn_done = dialog.findViewById(R.id.btn_done);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        EditText txt_namedb = dialog.findViewById(R.id.txt_namedb);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper = new DatabaseHelper(getApplicationContext(), txt_namedb.getText() + ".db", null, 1);
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openInputNameTbl(int gravity) {
        final Dialog dialog = new Dialog(this);
        wind(gravity, dialog, R.layout.input_name_tbl);
        Button btn_done = dialog.findViewById(R.id.btn_done);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        EditText txt_nameTbl = dialog.findViewById(R.id.txt_nameTbl);
        EditText txt_col1 = dialog.findViewById(R.id.txt_col1);
        EditText txt_col2 = dialog.findViewById(R.id.txt_col2);
        EditText txt_col3 = dialog.findViewById(R.id.txt_col3);
        EditText txt_col4 = dialog.findViewById(R.id.txt_col4);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryDrop = "Drop table if exists " + txt_nameTbl.getText();
                String query = "Create table " + txt_nameTbl.getText() + "(" + txt_col1.getText() + " text," + txt_col2.getText() + " text," + txt_col3.getText() + " text," + txt_col4.getText() + " text)";
                dbHelper.queryData(queryDrop);
                dbHelper.queryData(query);
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openInsertDataLop(int gravity) {
        final Dialog dialog = new Dialog(this);
        wind(gravity, dialog, R.layout.input_data_lop);
        Button btn_done = dialog.findViewById(R.id.btn_done);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        EditText name = dialog.findViewById(R.id.txt_name);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.insertDataLop(name.getText().toString());
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openDelDataLop(int gravity) {
        final Dialog dialog = new Dialog(this);
        wind(gravity, dialog, R.layout.del_data_lop);
        Button btn_done = dialog.findViewById(R.id.btn_done);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        EditText name = dialog.findViewById(R.id.txt_nameDel);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.deleteDataLop(name.getText().toString()) > 0) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void updateDataLop(int gravity) {
        final Dialog dialog = new Dialog(this);
        wind(gravity, dialog, R.layout.update_data);
        Button btn_done = dialog.findViewById(R.id.btn_done);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        EditText dataOld = dialog.findViewById(R.id.txt_dataOld);
        EditText dataNew = dialog.findViewById(R.id.txt_dataNew);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.updateDataLop(dataOld.getText().toString(), dataNew.getText().toString()) > 0) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void queryDataSvByLop(int gravity) {
        final Dialog dialog = new Dialog(this);
        wind(gravity, dialog, R.layout.show_data_sv);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_done = dialog.findViewById(R.id.btn_done);
        EditText malopbysv = dialog.findViewById(R.id.malopbysv);
        TextView showdata = dialog.findViewById(R.id.showdata);

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String show = "";
                Cursor cursor = dbHelper.getDataSv("sinhvien", malopbysv.getText().toString());
                while (cursor.moveToNext()) {
                    @SuppressLint("Range") String data1 = cursor.getString(cursor.getColumnIndex("masv"));
                    @SuppressLint("Range") String data2 = cursor.getString(cursor.getColumnIndex("tensv"));
                    @SuppressLint("Range") String data3 = cursor.getString(cursor.getColumnIndex("malop"));
                    if (data1 != null || data2 != null || data3 != null)
                        show += "- masv: " + data1.toString() + ", tensv: " + data2.toString() + ", malop: " + data3.toString() + "\n";
                }
                showdata.setText(show);
                cursor.close();
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void queryDataLop(int gravity) {
        final Dialog dialog = new Dialog(this);
        wind(gravity, dialog, R.layout.show_data_lop);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        TextView showdata = dialog.findViewById(R.id.showdata);
        String show = "";
        Cursor cursor = dbHelper.getDataTable("lop");
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String data1 = cursor.getString(cursor.getColumnIndex("malop"));
            @SuppressLint("Range") String data2 = cursor.getString(cursor.getColumnIndex("tenlop"));
            if (data1 != null || data2 != null)
                show += "- malop: " + data1.toString() + ", tenlop: " + data2.toString() + "\n";
        }
        showdata.setText(show);

        cursor.close();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void openInsertDataSv(int gravity, String ref) {
        final Dialog dialog = new Dialog(this);
        wind(gravity, dialog, R.layout.input_insert_student);
        Button btn_done = dialog.findViewById(R.id.btn_done);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        EditText name = dialog.findViewById(R.id.txt_name);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dbHelper.checkmalop(ref)) {
                    dbHelper.insertDataSv(name.getText().toString(), ref);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
