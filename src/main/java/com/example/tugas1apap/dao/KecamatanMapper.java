package com.example.tugas1apap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	@Select("select id, kode_kecamatan as kode_kecamatan, nama_kecamatan as nama_kecamatan from kecamatan where id_kota = #{id_kota}")
	public List <KecamatanModel> getAllKecamatanInCity (@Param("id_kota") String id_kota);
	
	@Select("select id, kode_kecamatan as kode_kecamatan, nama_kecamatan as nama_kecamatan from kecamatan where id = #{id_kecamatan}")
	public KecamatanModel getKecamatanById(@Param("id_kecamatan")String id_kecamatan);
	
	@Select("select id, kode_kecamatan as kode_kecamatan, nama_kecamatan as nama_kecamatan from kecamatan")
	public List <KecamatanModel> getAllKecamatan ();
}
