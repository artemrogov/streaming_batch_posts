package com.artemrogov.streaming.service.content;


import com.artemrogov.streaming.dto.blog.PostRequest;
import com.artemrogov.streaming.dto.datatable.DataTableRequest;
import com.artemrogov.streaming.dto.datatable.DataTableResultList;
import com.artemrogov.streaming.dto.datatable.PostDataRow;
import com.artemrogov.streaming.entities.Category;
import com.artemrogov.streaming.entities.Post;
import com.artemrogov.streaming.mapper.BlogMapper;
import com.artemrogov.streaming.repositories.CategoryRepository;
import com.artemrogov.streaming.repositories.PostDataRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentService implements IContentService{
    private final PostDataRepository postDataRepository;
    private final CategoryRepository categoryRepository;
    private final BlogMapper blogMapper;


    @Override
    public void updateRelationsByPostsIds(Long catId, List<Long> postsIds){
        if (postsIds.isEmpty()){
            return;
        }
        postsIds.forEach(id-> updatePivotalRelations(catId,id));
    }


    @Override
    public void removeRelationByPostsIds(Long catId, List<Long> postsIds){
        if (postsIds.isEmpty()){
            return;
        }
        postsIds.forEach(id-> removeRelation(catId,id));
    }

    @Override
    public void updatePivotalRelations(Long catId, Long postId){
        Category catCurrent = this.categoryRepository.findById(catId).orElseThrow();
        Post cPost = this.postDataRepository.findById(postId).orElseThrow();
        catCurrent.getPosts().add(cPost);
        cPost.getCategories().add(catCurrent);
        categoryRepository.save(catCurrent);
    }


    @Override
    public void removeRelation(Long catId, Long postId){
        Category catCurrent = this.categoryRepository.findById(catId).orElseThrow();
        Post cPost = this.postDataRepository.findById(postId).orElseThrow();
        catCurrent.getPosts().remove(cPost);
        cPost.getCategories().remove(catCurrent);
        categoryRepository.save(catCurrent);
    }


    @Override
    public DataTableResultList getPosts(Long limit, Long offset, Long draw) {

        List<PostDataRow> dataRows = postDataRepository.findPostsLimiterOffset(limit, offset)
                .stream().map(blogMapper::convertPostResponse)
                .collect(Collectors.toList());

         Long countPosts = this.postDataRepository.countPosts();

         return DataTableResultList.builder()
                 .data(dataRows)
                 .draw(draw)
                 .recordsTotal(countPosts)
                 .recordsFiltered(countPosts)
                 .build();
    }

    @Override
    public void createPosts(List<PostRequest> postRequests) {
         List<Post> postData = postRequests.stream().map(blogMapper::convertToPost).collect(Collectors.toList());
         this.postDataRepository.saveAll(postData);
    }

    @Override
    public PostDataRow create(PostRequest postRequest) {
        Post postCreated = this.postDataRepository.save(this.blogMapper.convertToPost(postRequest));
        return blogMapper.convertPostResponse(postCreated);
    }

    @Override
    public PostDataRow update(Long id, PostRequest postRequest) {
        Post post = this.postDataRepository.findById(id).orElseThrow();
        this.blogMapper.updateDataPost(postRequest,post);
        Post postUpdated = this.postDataRepository.save(post);
        return blogMapper.convertPostResponse(postUpdated);
    }

    @Override
    public PostDataRow getPostResponse(Long id) {
        return this.blogMapper.convertPostResponse(this.postDataRepository.findById(id).orElseThrow());
    }

    @Override
    public void destroy(Long id) {
        this.postDataRepository.deleteById(id);
    }

    @Override
    public void destroyByIds(List<Long> ids) {
        this.postDataRepository.deleteAllById(ids);
    }


    @Override
    public void generateExcelReport(DataTableRequest request) throws IOException {

        List<PostDataRow> dataRows = new ArrayList<>(getPosts(
                request.getLength(), request.getStart(), request.getDraw()).getData());

        File sFileSheet = new File(".");
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        sheet.setColumnWidth(0,6000);
        sheet.setColumnWidth(0,4000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();


        headerStyle.setBorderLeft(BorderStyle.HAIR);
        headerStyle.setBorderRight(BorderStyle.HAIR);
        headerStyle.setBorderBottom(BorderStyle.HAIR);

        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());

        headerStyle.setFillPattern(FillPatternType.BIG_SPOTS);

        XSSFFont font = workbook.createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 9);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("title");


        headerCell = header.createCell(2);
        headerCell.setCellStyle(headerStyle);
        headerCell.setCellValue("active");

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        style.setBorderLeft(BorderStyle.HAIR);
        style.setBorderRight(BorderStyle.HAIR);
        style.setBorderBottom(BorderStyle.HAIR);
        int rownum = 1;

        for (PostDataRow p : dataRows){

            Row row = sheet.createRow(rownum++);
            Cell cell = row.createCell(0);
            cell.setCellValue(p.getId());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(p.getTitle());
            cell.setCellStyle(style);


            boolean isActive = p.getActive() != null;

            cell = row.createCell(2);
            cell.setCellValue(isActive);
            cell.setCellStyle(style);
        }
        String path = sFileSheet.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "postsTest.xlsx";
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }
}
