package com.example.tugas1apap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("select id, kode_kelurahan as kode_kelurahan, id_kecamatan as id_kecamatan, nama_kelurahan as nama_kelurahan, kode_pos as kode_pos from kelurahan;")
	public List<KelurahanModel> getAllKelurahan ();
	
	@Select("select id, kode_kelurahan as kode_kelurahan, id_kecamatan as id_kecamatan, nama_kelurahan as nama_kelurahan, kode_pos as kode_pos from kelurahan where id_kecamatan = #{id_kecamatan}")
	public List<KelurahanModel> getAllKelurahanInKecamatan (@Param("id_kecamatan") String id_kecamatan);
	
	@Select("select * from kelurahan where id = #{id_kelurahan}")
	public KelurahanModel getKelurahanById(@Param("id_kelurahan") String id_kelurahan);
}
