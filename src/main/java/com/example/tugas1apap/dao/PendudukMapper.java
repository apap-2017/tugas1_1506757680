package com.example.tugas1apap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KeluargaModel;
import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	
		@Select("select * from penduduk p, keluarga k, kelurahan l, kecamatan c, kota t where nik = #{nik} and p.id_keluarga = k.id and k.id_kelurahan = l.id and l.id_kecamatan = c.id and c.id_kota = t.id")
		@Results(value = {
				@Result(property="kelurahan", column="nama_kelurahan"),
				@Result(property="kecamatan", column="nama_kecamatan"),
				@Result(property="kota", column="nama_kota")
		})
	    PendudukModel selectPenduduk (@Param("nik") String nik);
		
		@Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, is_wafat, agama, status_perkawinan, status_dalam_keluarga, pekerjaan, golongan_darah) VALUES ('${nik}', '${nama}', '${tempat_lahir}', '${tanggal_lahir}', '${jenis_kelamin}', '${is_wni}', '${id_keluarga}', '${is_wafat}', '${agama}', '${status_perkawinan}', '${status_dalam_keluarga}', '${pekerjaan}', '${golongan_darah}')")
	    void addPenduduk (PendudukModel penduduk);
		
		@Update("UPDATE penduduk SET nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, agama = #{agama}, pekerjaan = #{pekerjaan}, status_perkawinan = #{status_perkawinan}, status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat}  WHERE nik = #{nik}") 
	    void updatePenduduk (PendudukModel penduduk);
		
		@Update("UPDATE penduduk SET is_wafat = 1  WHERE nik = #{nik}") 
	    void pendudukIsWafat (@Param("nik") String nik);
		
		@Select("select * from penduduk where id_keluarga = #{id_keluarga}")
	    List <PendudukModel> selectFamilyMember (@Param("id_keluarga") String id_keluarga);
		
		@Select("select * from keluarga where id = #{id_keluarga}")
		KeluargaModel getFamily(@Param("id_keluarga") String id_keluarga);
		
		@Select("select * from penduduk p, keluarga k, kelurahan kl where p.id_keluarga = k.id and k.id_kelurahan = kl.id and kl.id = #{id_kelurahan}")
		List<PendudukModel> getPendudukKelurahan(@Param("id_kelurahan")String id_kelurahan);
		
		@Select("select kode_kecamatan from kecamatan kc, keluarga k, kelurahan kl where k.id = #{id_keluarga} and kl.id = k.id_kelurahan and kc.id=kl.id_kecamatan")
		String getKodeKecamatan(@Param("id_keluarga") String id_keluarga);
		
		@Select("select MAX(nik) from penduduk where nik LIKE #{param}")
		String getLastNIK(@Param("param") String param);
}
