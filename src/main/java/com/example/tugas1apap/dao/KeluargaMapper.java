package com.example.tugas1apap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.PendudukModel;
@Mapper
public interface KeluargaMapper 

{
	@Select("select k.nomor_kk as nomor_kk, k.id as id, k.alamat as alamat, k.rt as rt, k.rw as rw, k.id_kelurahan as id_kelurahan, l.nama_kelurahan as nama_kelurahan, c.nama_kecamatan as nama_kecamatan, t.nama_kota as nama_kota from keluarga k, kelurahan l, kecamatan c, kota t where nomor_kk = #{nomor_kk} and k.id_kelurahan = l.id and l.id_kecamatan = c.id and c.id_kota = t.id")
	@Results(value = {
			@Result(property="kelurahan", column="nama_kelurahan"),
			@Result(property="kecamatan", column="nama_kecamatan"),
			@Result(property="kota", column="nama_kota"),
			@Result(property="family_member", column="id",
			javaType=List.class, 
			many = @Many(select="selectFamilyMember"))
	})
    KeluargaModel selectKeluarga (@Param("nomor_kk") String nomor_kk);
	
	@Select("select * from keluarga where id = #{id}")
    KeluargaModel selectKeluargaById (@Param("id") String id);
	
	@Insert("insert into keluarga(nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) values ('${nomor_kk}', '${alamat}', '${rt}', '${rw}', '${id_kelurahan}', '${is_tidak_berlaku}')")
	void addKeluarga(KeluargaModel keluarga);
	
	@Select("select * from penduduk where id_keluarga = #{id_keluarga}")
    List <PendudukModel> selectFamilyMember (@Param("id_keluarga") String id_keluarga);
	
	@Update("UPDATE keluarga SET alamat = #{alamat}, rt = #{rt}, rw = #{rw}, id_kelurahan = #{id_kelurahan}  WHERE nomor_kk = #{nomor_kk} ") 
    void updateKeluarga (KeluargaModel keluarga);
	
	@Select("select * from kelurahan")
	List<KelurahanModel> getAllKelurahan();
	
	@Select("select kode_kecamatan from kecamatan kc, keluarga k, kelurahan kl where kl.id = #{id_kelurahan} and kc.id=kl.id_kecamatan")
	String getKodeKecamatan(@Param("id_kelurahan") String id_kelurahan);
	
	@Select("select MAX(nomor_kk) from keluarga where nomor_kk LIKE #{param}")
	String getLastNKK(@Param("param") String param);
}
