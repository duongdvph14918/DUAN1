/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import DAO.SanPhamDAO;
import MODEL.NhanVien;
import MODEL.SanPham;
import Utils.Auth;
import Utils.XImage;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STItemType;

/**
 *
 * @author Admin
 */
public class SanPhamform extends javax.swing.JInternalFrame {

    SanPhamDAO dao = new SanPhamDAO();
    int index;
    JFileChooser filchoos = new JFileChooser();

    /**
     * Creates new form SanPhamform
     */
    public SanPhamform() {
	initComponents();
    }

    void filltotable() {
	DefaultTableModel model = (DefaultTableModel) tblbang.getModel();
	model.setRowCount(0);
	try {
	    String keywword = txttimkiem.getText();
	    List<SanPham> list = dao.bykey(keywword);
	    for (SanPham sp : list) {
		Object[] row = {
		    sp.getMASP(),
		    sp.getTENSP(),
		    sp.getMALH(),
		    sp.getMANSX(),
		    sp.getMANHAPHANPHOI(),
		    sp.getSOLUONG(),
		    sp.getDONGIA(),
		    sp.getHINH()
		};
		model.addRow(row);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    void setform(SanPham sp) {
	cbomaloaiihang.removeAllItems();
	cbonhass.removeAllItems();
	cbonhapp.removeAllItems();
	txtmasanpham.setText(sp.getMASP());
	txtténanpham.setText(sp.getTENSP());
	cbomaloaiihang.addItem(sp.getMALH());
	cbonhass.addItem(sp.getMANSX());
	cbonhapp.addItem(sp.getMANHAPHANPHOI());
	txtsoluong.setText(String.valueOf(sp.getSOLUONG()));
	txtdongia.setText(String.valueOf(sp.getDONGIA()));
	if (sp.getHINH() != null) {
	    lblanh.setToolTipText(sp.getHINH());
	    lblanh.setIcon(XImage.read(sp.getHINH()));
	    /*
            ImageIcon readLogo(String tenFile) đọc file trong thư mục logos theo tên file trả về ImageIcon
            void setIcon(ImageIcon icon) set Icon cho lbl
	     */
	} else {
	    lblanh.setIcon(XImage.read("no name.png"));
	    lblanh.setToolTipText("no hình");
	}
    }

    SanPham getform() {
	SanPham sp = new SanPham();
	sp.setMASP(txtmasanpham.getText());
	sp.setTENSP(txtténanpham.getText());
	sp.setMALH(cbomaloaiihang.getSelectedItem() + "");
	sp.setMANSX(cbonhass.getSelectedItem() + "");
	sp.setMANHAPHANPHOI(cbonhapp.getSelectedItem() + "");
	sp.setSOLUONG(Integer.valueOf(txtsoluong.getText()));
	sp.setDONGIA(Double.valueOf(txtdongia.getText()));
	sp.setHINH(lblanh.getToolTipText()); //lấy tên hình
	return sp;
    }

    public void showdetail() {
	try {
	    String masx = tblbang.getValueAt(index, 0).toString();
	    SanPham model = dao.selectByID(masx);
	    if (model != null) {
		setform(model);
	    }
	    tblbang.setRowSelectionInterval(index, index);
	} catch (Exception e) {
	}
    }

    void chonanh() {
	if (filchoos.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
	    File file = filchoos.getSelectedFile();
	    XImage.save(file);
	    try {
		Image img = ImageIO.read(file);
		int height = lblanh.getWidth();
		int widt = lblanh.getHeight();
		lblanh.setIcon(new ImageIcon(img.getScaledInstance(height, widt, 0)));
	    } catch (Exception e) {
	    }
	    lblanh.setToolTipText(file.getName());
	}
    }

    void loadmaloaihang() {
	DefaultComboBoxModel model = (DefaultComboBoxModel) cbomaloaiihang.getModel();
	
	try {
	    List<SanPham> list = dao.selectAll();
	    for (SanPham sp : list) {
		model.addElement(sp.getMALH());
	    }
	} catch (Exception e) {
	}
    }

    void loadnhapp() {
	DefaultComboBoxModel model = (DefaultComboBoxModel) cbonhapp.getModel();
	
	try {
	    List<SanPham> list = dao.selectAll();
	    for (SanPham sp : list) {
		model.addElement(sp.getMANHAPHANPHOI());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    void loadnhasx() {
	DefaultComboBoxModel model = (DefaultComboBoxModel) cbonhass.getModel();
	
	try {
	    List<SanPham> list = dao.selectAll();
	    for (SanPham sp : list) {
		model.addElement(sp.getMANSX());
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    void clear() {
	SanPham sp = new SanPham();
	setform(sp);
	index = -1;
    }

    void delete() {
	if (!Auth.ismaneger()) {
	    JOptionPane.showMessageDialog(this, "Bạn không có quyền xóa");
	} else {
	    String ma = txtmasanpham.getText();
	    try {
		dao.delete(ma);
		filltotable();
		clear();
		JOptionPane.showMessageDialog(this, "Xóa thành công");
	    } catch (Exception e) {
		JOptionPane.showMessageDialog(this, "Xóa thất bại");
	    }

	}
    }

    void insert() {
	SanPham sp = getform();
	try {
	    dao.insert(sp);
	    filltotable();
	    clear();
	    JOptionPane.showMessageDialog(this, "Thêm thành công");
	} catch (Exception e) {
	    JOptionPane.showMessageDialog(this, "trùng mã");
	}
    }

    void update() {
	SanPham sp = getform();
	try {
	    dao.update(sp);
	    filltotable();
	    clear();
	    JOptionPane.showMessageDialog(this, "Sử thành công");
	} catch (Exception e) {
	    JOptionPane.showMessageDialog(this, "Sửa thất bại");
	}
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblanh = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txttimkiem = new javax.swing.JTextField();
        btntimkiem = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtmasanpham = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtténanpham = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtdongia = new javax.swing.JTextField();
        cbonhass = new javax.swing.JComboBox<>();
        cbonhapp = new javax.swing.JComboBox<>();
        cbomaloaiihang = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblbang = new javax.swing.JTable();
        btnthem = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnxoa = new javax.swing.JButton();
        btnmoi = new javax.swing.JButton();
        btntruoc = new javax.swing.JButton();
        btndau = new javax.swing.JButton();
        btncuoi = new javax.swing.JButton();
        btnsau = new javax.swing.JButton();
        txtsoluong = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 11), new java.awt.Color(0, 51, 204))); // NOI18N

        lblanh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblanhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblanh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblanh, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel2.setText("Quản Lý Sản phẩm");

        jLabel3.setText("Logo");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel4.setText("Tìm kiếm");

        btntimkiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Find.png"))); // NOI18N
        btntimkiem.setText("Tìm kiếm");
        btntimkiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btntimkiemMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(23, 23, 23)
                .addComponent(txttimkiem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btntimkiem)
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btntimkiem)
                    .addComponent(txttimkiem, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setText("Mã Sản phẩm");

        jLabel6.setText("Tên sản phẩm");

        jLabel7.setText("Mã nhà SX");

        jLabel8.setText("Mã loại hàng");

        jLabel9.setText("Số lượng");

        jLabel11.setText("Đơn giá");

        jLabel12.setText("Mã nhà pp");

        cbonhass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbonhass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbonhassActionPerformed(evt);
            }
        });

        cbonhapp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbonhappActionPerformed(evt);
            }
        });

        cbomaloaiihang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbomaloaiihang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbomaloaiihangActionPerformed(evt);
            }
        });

        tblbang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID sản phẩm", "Tên san phẩm", "Mã loại hàng", "Nhà sản xuất", "Nhà phân phối", "Số lượng", "Đơn giá", "Hình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblbang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblbangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblbang);

        btnthem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Add.png"))); // NOI18N
        btnthem.setText("Thêm");
        btnthem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Edit.png"))); // NOI18N
        jButton2.setText("Sủa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnxoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Delete.png"))); // NOI18N
        btnxoa.setText("Xóa");
        btnxoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnxoaActionPerformed(evt);
            }
        });

        btnmoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        btnmoi.setText("Mới");
        btnmoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmoiActionPerformed(evt);
            }
        });

        btntruoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/lui.png"))); // NOI18N
        btntruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntruocActionPerformed(evt);
            }
        });

        btndau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dau.png"))); // NOI18N
        btndau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndauActionPerformed(evt);
            }
        });

        btncuoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cuoi.png"))); // NOI18N
        btncuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncuoiActionPerformed(evt);
            }
        });

        btnsau.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/tien.png"))); // NOI18N
        btnsau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsauActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(65, 65, 65)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(32, 32, 32)
                                        .addComponent(txtmasanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtténanpham, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel7))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cbonhass, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cbomaloaiihang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel11))
                                        .addGap(47, 47, 47)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtdongia, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                                            .addComponent(cbonhapp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtsoluong))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnthem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnxoa)
                .addGap(12, 12, 12)
                .addComponent(btnmoi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                .addComponent(btndau)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btntruoc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnsau)
                .addGap(18, 18, 18)
                .addComponent(btncuoi)
                .addGap(84, 84, 84))
            .addGroup(layout.createSequentialGroup()
                .addGap(334, 334, 334)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtmasanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtténanpham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(cbomaloaiihang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(cbonhass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(cbonhapp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtsoluong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtdongia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btncuoi)
                            .addComponent(btnsau)
                            .addComponent(btntruoc)
                            .addComponent(btndau)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnthem)
                            .addComponent(jButton2)
                            .addComponent(btnxoa)
                            .addComponent(btnmoi, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btndauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndauActionPerformed
	// TODO add your handling code here:
	index = 0;
	showdetail();
    }//GEN-LAST:event_btndauActionPerformed

    private void btntimkiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btntimkiemMouseClicked
	// TODO add your handling code here:
	filltotable();
    }//GEN-LAST:event_btntimkiemMouseClicked

    private void tblbangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblbangMouseClicked
	// TODO add your handling code here:
	index = tblbang.getSelectedRow();
	showdetail();
    }//GEN-LAST:event_tblbangMouseClicked

    private void btncuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncuoiActionPerformed
	// TODO add your handling code here:
	index = tblbang.getRowCount() - 1;
	showdetail();
    }//GEN-LAST:event_btncuoiActionPerformed

    private void btntruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntruocActionPerformed
	// TODO add your handling code here:
	index--;
	showdetail();
    }//GEN-LAST:event_btntruocActionPerformed

    private void btnsauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsauActionPerformed
	// TODO add your handling code here:
	index++;
	showdetail();
    }//GEN-LAST:event_btnsauActionPerformed

    private void lblanhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblanhMouseClicked
	// TODO add your handling code here:
	chonanh();
    }//GEN-LAST:event_lblanhMouseClicked

    private void cbomaloaiihangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbomaloaiihangActionPerformed
	// TODO add your handling code here:
	loadmaloaihang();
    }//GEN-LAST:event_cbomaloaiihangActionPerformed

    private void btnthemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemActionPerformed
	// TODO add your handling code here:
	insert();
    }//GEN-LAST:event_btnthemActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
	// TODO add your handling code here:
//	loadmaloaihang();
//	loadnhasx();
//	loadnhapp();
	filltotable();
//	cbomaloaiihang.removeAllItems();
//	cbonhapp.removeAllItems();
//	cbonhass.removeAllItems();
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnxoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnxoaActionPerformed
	// TODO add your handling code here:
	int hoi = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa không ");
	if (hoi != JOptionPane.YES_NO_OPTION) {
	    if (Auth.user.isVAITRO()) {
		delete();
	    } else {
		JOptionPane.showMessageDialog(this, "Chỉ quản lý được xóa");
	    }
	}

    }//GEN-LAST:event_btnxoaActionPerformed

    private void btnmoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmoiActionPerformed
	// TODO add your handling code here:
	clear();
    }//GEN-LAST:event_btnmoiActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
	// TODO add your handling code here:
	update();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbonhassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbonhassActionPerformed
	// TODO add your handling code here:
	loadnhasx();
    }//GEN-LAST:event_cbonhassActionPerformed

    private void cbonhappActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbonhappActionPerformed
	// TODO add your handling code here:
	loadnhapp();
    }//GEN-LAST:event_cbonhappActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncuoi;
    private javax.swing.JButton btndau;
    private javax.swing.JButton btnmoi;
    private javax.swing.JButton btnsau;
    private javax.swing.JButton btnthem;
    private javax.swing.JButton btntimkiem;
    private javax.swing.JButton btntruoc;
    private javax.swing.JButton btnxoa;
    private javax.swing.JComboBox<String> cbomaloaiihang;
    private javax.swing.JComboBox<String> cbonhapp;
    private javax.swing.JComboBox<String> cbonhass;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblanh;
    private javax.swing.JTable tblbang;
    private javax.swing.JTextField txtdongia;
    private javax.swing.JTextField txtmasanpham;
    private javax.swing.JTextField txtsoluong;
    private javax.swing.JTextField txttimkiem;
    private javax.swing.JTextField txtténanpham;
    // End of variables declaration//GEN-END:variables
}
