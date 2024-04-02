package vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.whatsapp;


import static vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.fragment.whatsapp.MediaDownloadFragment.mutableLiveDatas;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.documentfile.provider.DocumentFile;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.R;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.ConstMedia;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.model.insta_model.StoryModelMedia;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.utils.MediaFilePathUtility;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.utils.iUtilsMedia;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.whatsapp.MediaWpImageViewerActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.whatsapp.MediaWpStatusActivity;
import vidmatemaster.fastvideodownloader.allvideodownloader.hdvideodownloader.whatsapp.MediaWpVIdeoViewerActivity;
import com.google.android.material.imageview.ShapeableImageView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntFunction;


public class MediaImageFragment extends Fragment {
    public String SaveFilePath = (ConstMedia.RootDirectoryWhatsappShow + "/");
    public ArrayList<Object> data;
    RecyclerView recycler_view;
    LinearLayout empty_list;
    private static final String DATA_KEY = "data";

    public MediaImageFragment() {}

    ArrayList<Object> filesList = new ArrayList<>();
    private File[] files;

    private DocumentFile[] getFromSdcard() {
        DocumentFile fromTreeUri = DocumentFile.fromTreeUri(requireActivity(), Uri.parse(MediaWpStatusActivity.namedataprefs));
        if (fromTreeUri == null || !fromTreeUri.exists() || !fromTreeUri.isDirectory() || !fromTreeUri.canRead() || !fromTreeUri.canWrite()) {
            return null;
        }
        return fromTreeUri.listFiles();
    }

    private ArrayList<Object> getData() {
        int i = 0;
        if (Build.VERSION.SDK_INT >= 30) {
            if (filesList != null) {
                filesList = new ArrayList<>();
            }
            try {
                DocumentFile[] fromSdcard = getFromSdcard();
                int length = fromSdcard.length;
                while (i < length) {
                    DocumentFile documentFile = fromSdcard[i];
                    Uri uri = documentFile.getUri();
                    StoryModelMedia storyModel = new StoryModelMedia();
                    storyModel.setName("Download");
                    storyModel.setUri(uri);
                    storyModel.setPath(documentFile.getUri().toString());
                    storyModel.setFilename(documentFile.getUri().getLastPathSegment());
                    storyModel.setPack("com.whatsapp");
                    PrintStream printStream = System.out;
                    printStream.println("dhasjhdahsdhas " + documentFile.getUri().toString());
                    if (!documentFile.getUri().toString().contains(".nomedia") && !documentFile.getUri().toString().equals("")) {
                        this.filesList.add(storyModel);
                    }
                    i++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (this.filesList != null) {
                this.filesList = new ArrayList<>();
            }
            requireActivity().getSharedPreferences("whatsapp_pref", 0).getString("whatsapp", "main");
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + ConstMedia.FOLDER_NAME + "Media/.Statuses");
            File file2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + ConstMedia.FOLDER_NAME_Whatsapp_and11 + "Media/.Statuses");
            final ArrayList arrayList = new ArrayList(Arrays.asList(file.listFiles() != null ? file.listFiles() : new File[]{new File("")}));
            arrayList.addAll(Arrays.asList(file2.listFiles() != null ? file2.listFiles() : new File[]{new File("")}));
            File[] fileArr = new File[arrayList.size()];
            if (Build.VERSION.SDK_INT >= 24) {
                Arrays.setAll(fileArr, new IntFunction() {
                    @Override
                    public Object apply(int i) {
                        return (File) arrayList.get(i);
                    }
                });
                this.files = fileArr;
            } else {
                this.files = file.listFiles();
            }
            try {
                Arrays.sort(this.files, new Comparator() {
                    @Override
                    public int compare(Object obj, Object obj2) {
                        File file = (File) obj;
                        File file2 = (File) obj2;
                        if (file.lastModified() > file2.lastModified()) {
                            return -1;
                        }
                        return file.lastModified() < file2.lastModified() ? 1 : 0;
                    }
                });
                while (true) {
                    File[] fileArr2 = this.files;
                    if (i >= fileArr2.length) {
                        break;
                    }
                    File file3 = fileArr2[i];
                    StoryModelMedia storyModel2 = new StoryModelMedia();
                    storyModel2.setName("Download");
                    storyModel2.setUri(Uri.fromFile(file3));
                    storyModel2.setPath(this.files[i].getAbsolutePath());
                    storyModel2.setFilename(file3.getName());
                    storyModel2.setPack("com.whatsapp");
                    if (!file3.getName().equals(".nomedia") && !file3.getPath().equals("")) {
                        this.filesList.add(storyModel2);
                    }
                    i++;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return this.filesList;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.media_fragment_download, viewGroup, false);
        recycler_view = inflate.findViewById(R.id.recyclerSingleVideolist);
        empty_list = inflate.findViewById(R.id.empty_list);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageGet();
    }

    private void ImageGet() {
        if (data == null) {
            data = getData();
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.data.size(); i++) {
            StoryModelMedia storyModel = (StoryModelMedia) this.data.get(i);
            if (storyModel.getUri().toString().endsWith(".jpg") ||
                storyModel.getUri().toString().endsWith(".mp4")||
                storyModel.getUri().toString().endsWith(".png")) {
                arrayList.add(storyModel);
            }
        }
        if (arrayList.size() == 0) {
            empty_list.setVisibility(View.VISIBLE);
            return;
        }
        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2);
        recycler_view.setLayoutManager(layoutManager);
        ImageAdapterAllHD imageAdapter = new ImageAdapterAllHD(requireActivity());
        recycler_view.setAdapter(imageAdapter);
        imageAdapter.notify(arrayList);
    }

    public class ImageAdapterAllHD extends RecyclerView.Adapter<ImageAdapterAllHD.ViewHolder> {
        FragmentActivity requireActivity;
        ArrayList<Object> data;

        public ImageAdapterAllHD(FragmentActivity requireActivity) {
            this.requireActivity = requireActivity;
        }

        public void notify(ArrayList arrayList) {
            this.data = new ArrayList<>(arrayList);
            notifyItemRangeInserted(0, arrayList.size());
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(requireActivity()).inflate(R.layout.media_item_image_wa, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ViewHolder viewHolder = (ViewHolder) holder;
            StoryModelMedia storyModel = (StoryModelMedia) data.get(position);

            if (storyModel.getFilename() != null && storyModel.getFilename().endsWith(".mp4")) {
                holder.play.setVisibility(View.VISIBLE);
            } else {
                holder.play.setVisibility(View.GONE);
            }
            (Glide.with(requireActivity()).load(storyModel.getUri()).centerCrop()).into(viewHolder.picImg);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (storyModel.getFilename() != null && storyModel.getFilename().endsWith(".mp4")) {
                        video_next(storyModel, 0);
                    } else {
                        next(storyModel, 1);
                    }
                }
            });

            holder.downloadID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (storyModel.getFilename() != null && storyModel.getFilename().endsWith(".mp4")) {
                        video_next(storyModel, 1);
                    } else {
                        next(storyModel, 0);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView play, downloadID;
            ShapeableImageView picImg;

            public ViewHolder(View view) {
                super(view);
                play =  view.findViewById(R.id.play);
                downloadID = view.findViewById(R.id.downloadID);
                picImg = view.findViewById(R.id.picImg);
            }
        }
    }

    public static void createFileFolder() {
        if (!ConstMedia.RootDirectoryWhatsappShow.exists()) {
            ConstMedia.RootDirectoryWhatsappShow.mkdirs();
        }
    }

    public void next(final StoryModelMedia storyModel, int i) {
        if (i == 0) {
            if (Build.VERSION.SDK_INT >= 30) {
                iUtilsMedia.checkFolder();
                try {
                    MediaWpStatusActivity recentStatusActivity = (MediaWpStatusActivity) requireActivity();
                    MediaFilePathUtility.moveFile(recentStatusActivity, DocumentFile.fromSingleUri(recentStatusActivity, Uri.parse(storyModel.getPath())).getUri().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                iUtilsMedia.scanFile(requireActivity(), storyModel.getFilename());
                mutableLiveDatas.postValue("");
                Toast.makeText(requireActivity(), R.string.saved, Toast.LENGTH_SHORT).show();
                return;
            }
            createFileFolder();
            String path = storyModel.getPath();
            String substring = path.substring(path.lastIndexOf("/") + 1);
            try {
                FileUtils.copyFileToDirectory(new File(path), new File(this.SaveFilePath));
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            String substring2 = substring.substring(12);
            MediaWpStatusActivity recentStatusActivity2 = (MediaWpStatusActivity) requireActivity();
            String[] strArr = {new File(this.SaveFilePath + substring2).getAbsolutePath()};
            String[] strArr2 = new String[1];
            strArr2[0] = storyModel.getUri().toString().endsWith(".mp4") ? "video/*" : "image/*";
            MediaScannerConnection.scanFile(recentStatusActivity2, strArr, strArr2, new MediaScannerConnection.MediaScannerConnectionClient() {
                public void onMediaScannerConnected() {}

                public void onScanCompleted(String str, Uri uri) {}
            });
            new File(this.SaveFilePath, substring).renameTo(new File(this.SaveFilePath, substring2));
            MediaWpStatusActivity recentStatusActivity3 = (MediaWpStatusActivity) requireActivity();
            Toast.makeText(recentStatusActivity3, requireActivity().getResources().getString(R.string.saved_to) + this.SaveFilePath + substring2, Toast.LENGTH_SHORT).show();
            mutableLiveDatas.postValue("");
        } else if (i == 1) {
            Intent intent = new Intent(requireActivity(), MediaWpImageViewerActivity.class);
            intent.putExtra("image", storyModel.getUri().toString());
            intent.putExtra("type", "image");
            intent.putExtra("pack", storyModel.getPack());
            String[] name = storyModel.getFilename().split("/");
            intent.putExtra("name", name[name.length-1]);
            intent.putExtra("from","whatsapp");
            requireActivity().startActivity(intent);
        }
    }

    public void video_next(final StoryModelMedia storyModel, int i) {
        if (i == 0) {
            Intent intent = new Intent(requireActivity(), MediaWpVIdeoViewerActivity.class);
            intent.putExtra("video", storyModel.getUri().toString());
            intent.putExtra("type", "video");
            intent.putExtra("pack", storyModel.getPack());
            String[] name = storyModel.getFilename().split("/");
            intent.putExtra("name", name[name.length-1]);
            intent.putExtra("from","whatsapp");
            requireActivity().startActivity(intent);
        } else if (i == 1) {
            if (Build.VERSION.SDK_INT >= 30) {
                iUtilsMedia.checkFolder();
                try {
                    MediaWpStatusActivity recentStatusActivity = (MediaWpStatusActivity) requireActivity();
                    MediaFilePathUtility.moveFile(recentStatusActivity, DocumentFile.fromSingleUri(recentStatusActivity, Uri.parse(storyModel.getPath())).getUri().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                iUtilsMedia.scanFile(requireActivity(), storyModel.getFilename());
                Toast.makeText(requireActivity(), R.string.saved, Toast.LENGTH_SHORT).show();
                mutableLiveDatas.postValue("");
                return;
            }
            createFileFolder();
            String path = storyModel.getPath();
            String substring = path.substring(path.lastIndexOf("/") + 1);
            try {
                FileUtils.copyFileToDirectory(new File(path), new File(this.SaveFilePath));
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            String substring2 = substring.substring(12);
            MediaWpStatusActivity recentStatusActivity2 = (MediaWpStatusActivity) requireActivity();
            String[] strArr = {new File(this.SaveFilePath + substring2).getAbsolutePath()};
            String[] strArr2 = new String[1];
            strArr2[0] = storyModel.getUri().toString().endsWith(".mp4") ? "video/*" : "image/*";
            MediaScannerConnection.scanFile(recentStatusActivity2, strArr, strArr2, new MediaScannerConnection.MediaScannerConnectionClient() {
                public void onMediaScannerConnected() {
                }

                public void onScanCompleted(String str, Uri uri) {
                }
            });
            new File(this.SaveFilePath, substring).renameTo(new File(this.SaveFilePath, substring2));
            MediaWpStatusActivity recentStatusActivity3 = (MediaWpStatusActivity) requireActivity();
            Toast.makeText(recentStatusActivity3, requireActivity().getResources().getString(R.string.saved_to) + this.SaveFilePath + substring2, Toast.LENGTH_SHORT).show();
            mutableLiveDatas.postValue("");
        }
    }
}
